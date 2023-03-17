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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static co.com.banco.model.common.ValidationUtils.validarMovimiento;

@RequiredArgsConstructor
public class MovimientoUseCase {

    public static final String DEBITO = "DEBITO";
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
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        Boolean validacionCamposMovimiento = validarMovimiento(movimiento);
        Boolean validacionExistenciaMovimientoClientePersona = MovimientoClientePersona(movimiento);
        if (validacionExistenciaMovimientoClientePersona && validacionCamposMovimiento) {
            Movimiento movimientoAplicado = aplicarMovimiento(movimiento);
            Cuenta cuentaConNuevoSaldo = aplicarNuevoSaldoEnCuenta(movimientoAplicado.getCuenta());
            movimientoAplicado.setCuenta(cuentaConNuevoSaldo);
            return movimientoRepository.crearMovimiento(movimientoAplicado);
        }
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }


    private Movimiento aplicarMovimiento(Movimiento movimiento) {
        Movimiento movimientoAplicado = movimiento;
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());
        if (movimiento.getTipoMovimiento().equalsIgnoreCase(DEBITO)) {
            Long saldoDisponible = validarSaldoDisponibleEnCuenta(movimiento.getCuenta().getId(), movimiento.getValorMovimiento());
            if (saldoDisponible > 0L) {
                movimientoAplicado.setSaldo(cuentaConsultada.getSaldoInicial() - movimiento.getValorMovimiento());
                movimientoAplicado.getCuenta().setSaldoInicial(movimientoAplicado.getSaldo());
                movimientoAplicado.setValorMovimiento(movimiento.getValorMovimiento() * -1L);
            } else {
                throw new BusinessException(BusinessException.Type.SALDO_INFERIOR_CERO);
            }
        } else {
            movimientoAplicado.setSaldo(cuentaConsultada.getSaldoInicial() + movimiento.getValorMovimiento());
            movimientoAplicado.getCuenta().setSaldoInicial(movimientoAplicado.getSaldo());
        }
        return movimientoAplicado;
    }

    private Long validarSaldoDisponibleEnCuenta(Integer idCuenta, Long movimientoDebito) {
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(idCuenta);
        if (cuentaConsultada.getSaldoInicial() - movimientoDebito > 0L) {
            return cuentaConsultada.getSaldoInicial() - movimientoDebito;
        }
        return 0L;
    }

    private Cuenta aplicarNuevoSaldoEnCuenta(Cuenta cuenta) {
        Cuenta cuentaConNuevoSaldo = cuenta;
        cuentaConNuevoSaldo.setSaldoInicial(cuenta.getSaldoInicial());
        return cuentaRepository.guardarCuenta(cuentaConNuevoSaldo);
    }

    private Boolean MovimientoClientePersona(Movimiento movimiento) {
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

    public List<Movimiento> generarReporteEntreFechas(String  inicio, String fin) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date fechaInicio = null;
        Date fechaFinal = null;
        try {
            fechaInicio = formatter.parse(inicio);
            fechaFinal = formatter.parse(fin);
        } catch (ParseException e) {
            throw new BusinessException(BusinessException.Type.FORMATO_FECHA_INVALID);
        }

        if (fechaInicio.after(fechaFinal)) {
            throw new BusinessException(BusinessException.Type.FECHA_INICIAL_MAYOR);
        }

        return movimientoRepository.generarReporteEntreFechas(fechaInicio, fechaFinal);
    }
}
