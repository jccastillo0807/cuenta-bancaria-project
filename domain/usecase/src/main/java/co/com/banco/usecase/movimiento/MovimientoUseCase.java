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

import static co.com.banco.model.common.ValidationUtils.*;


@RequiredArgsConstructor
public class MovimientoUseCase {

    public static final String DEBITO = "DEBITO";
    public static final String CREDITO = "CREDITO";

    private final MovimientoDebitoUseCase movimientoDebitoUseCase;
    private final MovimientoCreditoUseCase movimientoCreditoUseCase;
    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;


    public List<Movimiento> obtenerMovimientos() {
        return movimientoRepository.verMovimientos();
    }

    public Movimiento encontrarPorId(Integer id) {
        validarIdNulo(id);
        Movimiento movimientoEncontrado = movimientoRepository.encontrarPorId(id);
        if (Objects.isNull(movimientoEncontrado)) {
            throw new BusinessException(BusinessException.Type.MOVIMIENTO_NO_ENCONTRADO);
        }
        return movimientoEncontrado;
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        validarCamposMovimiento(movimiento);
        validarEstructuraMovimiento(movimiento);
        if (movimiento.getValorMovimiento() <= 0L) {
            throw new BusinessException(BusinessException.Type.MOVIMIENTO_NO_PERMITIDO);
        }
        Movimiento movimientoAplicado = aplicarMovimiento(movimiento);
        Cuenta cuentaConNuevoSaldo = guardarNuevoSaldoEnCuenta(movimientoAplicado.getCuenta());
        movimientoAplicado.setCuenta(cuentaConNuevoSaldo);
        return movimientoRepository.crearMovimiento(movimientoAplicado);
    }

    public void eliminarMovimiento(Integer id) {
        encontrarPorId(id);
        movimientoRepository.deleteById(id);
    }

    public Movimiento actualizarMovimiento(Integer id, Movimiento movimiento) {
        validarPathConId(id, movimiento.getId());
        Movimiento movimientoEnBaseDatos = encontrarPorId(id);
        validarSiEsUltimoMovimiento(movimiento);
        movimientoEnBaseDatos.setFechaMovimiento(movimiento.getFechaMovimiento());
        movimientoEnBaseDatos.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoEnBaseDatos.setValorMovimiento(movimiento.getValorMovimiento());
        movimientoEnBaseDatos.setSaldo(movimiento.getSaldoAnterior());
        return guardarMovimiento(movimientoEnBaseDatos);
    }

    private Movimiento aplicarMovimiento(Movimiento movimiento) {
        switch (movimiento.getTipoMovimiento().toUpperCase()) {

            case DEBITO:
                return movimientoDebitoUseCase.construirMovimientoDebito(movimiento);

            case CREDITO:
                return movimientoCreditoUseCase.construirMovimientoCredito(movimiento);

            default:
                throw new BusinessException(BusinessException.Type.TIPO_MOVIMIENTO_NO_VALIDO);
        }
    }

    private Cuenta guardarNuevoSaldoEnCuenta(Cuenta cuenta) {
        return cuentaRepository.guardarCuenta(cuenta);
    }

    private void validarEstructuraMovimiento(Movimiento movimiento) {
        Persona existePersona = personaRepository.encontrarPersonaPorId(
                movimiento.getCuenta().getCliente().getPersona().getId());
        Cliente existeCliente = clienteRepository.encontrarPorId(movimiento.getCuenta().getCliente().getId());
        Cuenta existeCuenta = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());

        if (Objects.isNull(existePersona)) {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
        if (Objects.isNull(existeCliente)) {
            throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
        }
        if (Objects.isNull(existeCuenta)) {
            throw new BusinessException(BusinessException.Type.ERROR_CUENTA_NO_REGISTRADO);
        }
    }

    private void validarSiEsUltimoMovimiento(Movimiento movimiento) {
        List<Movimiento> movimientosCuenta =
                movimientoRepository.encontrarMovimientosPorCuentaAsociada(movimiento.getCuenta().getId());
        movimientosCuenta.forEach(
                movimientoActual -> {
                    if (movimientoActual.getId() > movimiento.getId()) {
                        throw new BusinessException(BusinessException.Type.MOVIMIENTO_NO_ES_ULTIMO);
                    }
                }
        );
    }
}
