package co.com.banco.usecase.movimiento;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.movimiento.gateways.MovimientoRepository;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static co.com.banco.model.common.ValidationUtils.validarMovimiento;

@RequiredArgsConstructor
public class MovimientoUseCase {

    public static final String DEBITO = "DEBITO";
    public static final String CREDITO = "CREDITO";
    private final MovimientoRepository movimientoRepository;

    private final CuentaRepository cuentaRepository;
    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;

    public List<Movimiento> obtenerMovimientos() {
        return movimientoRepository.verMovimientos();
    }

    public Movimiento findById(Integer id) {
        if (id != null) {
            Movimiento movimientoEncontrado = movimientoRepository.encontrarPorId(id);
            if (!Objects.isNull(movimientoEncontrado)) {
                return movimientoEncontrado;
            } else {
                throw new BusinessException(BusinessException.Type.MOVIMIENTO_NO_ENCONTRADO);
            }
        }
        throw new BusinessException(BusinessException.Type.ID_NULL);
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        Boolean validacionCamposMovimiento = validarMovimiento(movimiento);
        Boolean validacionExistenciaMovimientoClientePersona = movimientoClientePersona(movimiento);
        if (validacionExistenciaMovimientoClientePersona && validacionCamposMovimiento) {
            Movimiento movimientoAplicado = aplicarMovimiento(movimiento);
            Cuenta cuentaConNuevoSaldo = aplicarNuevoSaldoEnCuenta(movimientoAplicado.getCuenta());
            movimientoAplicado.setCuenta(cuentaConNuevoSaldo);
            return movimientoRepository.crearMovimiento(movimientoAplicado);
        }
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    public void eliminarMovimiento(Integer id) {
        Movimiento movimientoEncontrado = movimientoRepository.encontrarPorId(id);
        if (Objects.isNull(movimientoEncontrado)) {
            throw new BusinessException(BusinessException.Type.MOVIMIENTO_NO_ENCONTRADO);
        } else {
            movimientoRepository.deleteById(id);
        }
    }

    public Movimiento actualizarMovimiento(Integer id, Movimiento movimiento) {
        if (id == null) {
            throw new BusinessException(BusinessException.Type.ID_NULL);
        }
            Movimiento movimientoEnBaseDatos = movimientoRepository.encontrarPorId(id);
            if (Objects.nonNull(movimientoEnBaseDatos)) {
                movimientoEnBaseDatos.setFechaMovimiento(movimiento.getFechaMovimiento());
                movimientoEnBaseDatos.setTipoMovimiento(movimiento.getTipoMovimiento());
                movimientoEnBaseDatos.setValorMovimiento(movimiento.getValorMovimiento());
                return guardarMovimiento(movimientoEnBaseDatos);
            }
            throw new BusinessException(BusinessException.Type.MOVIMIENTO_NO_ENCONTRADO);
    }

    private Movimiento aplicarMovimiento(Movimiento movimiento) {
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());
        if (movimiento.getTipoMovimiento().equalsIgnoreCase(DEBITO)) {
            Long saldoDisponible = validarSaldoDisponibleEnCuenta(movimiento.getCuenta().getId(), movimiento.getValorMovimiento());
            if (saldoDisponible > 0L) {
                movimiento.setSaldo(cuentaConsultada.getSaldoInicial() - movimiento.getValorMovimiento());
                movimiento.getCuenta().setSaldoInicial(movimiento.getSaldo());
                movimiento.setValorMovimiento(movimiento.getValorMovimiento() * -1L);
            } else {
                throw new BusinessException(BusinessException.Type.SALDO_INFERIOR_CERO);
            }
        } else {
            if (movimiento.getTipoMovimiento().equalsIgnoreCase(CREDITO)) {
                movimiento.setSaldo(cuentaConsultada.getSaldoInicial() + movimiento.getValorMovimiento());
                movimiento.getCuenta().setSaldoInicial(movimiento.getSaldo());
            } else {
                throw new BusinessException(BusinessException.Type.TIPO_MOVIMIENTO_NO_VALIDO);
            }
        }
        return movimiento;
    }

    private Long validarSaldoDisponibleEnCuenta(Integer idCuenta, Long movimientoDebito) {
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(idCuenta);
        return Math.max(cuentaConsultada.getSaldoInicial() - movimientoDebito, 0L);
    }

    private Cuenta aplicarNuevoSaldoEnCuenta(Cuenta cuenta) {
//        cuenta.setSaldoInicial(cuenta.getSaldoInicial());
        return cuentaRepository.guardarCuenta(cuenta);
    }

    private Boolean movimientoClientePersona(Movimiento movimiento) {
        Persona existePersona = personaRepository.encontrarPersonaPorId(movimiento.getCuenta().getCliente().getPersona().getId());
        Cliente existeCliente = clienteRepository.encontrarPorId(movimiento.getCuenta().getCliente().getId());
        Cuenta existeCuenta = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());

        if (Objects.nonNull(existePersona)) {
            if (Objects.nonNull(existeCliente)) {
                if (Objects.nonNull(existeCuenta)) {
                    return true;
                } else {
                    throw new BusinessException(BusinessException.Type.ERROR_CUENTA_NO_REGISTRADO);
                }
            } else {
                throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
            }
        } else {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
    }

}
