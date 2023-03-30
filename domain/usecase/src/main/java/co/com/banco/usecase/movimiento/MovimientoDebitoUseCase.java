package co.com.banco.usecase.movimiento;

import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import co.com.banco.model.movimiento.Movimiento;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class MovimientoDebitoUseCase {
    private final CuentaRepository cuentaRepository;

    public Movimiento construirMovimientoDebito(Movimiento movimiento) {
        movimiento.setValorMovimiento(movimiento.getValorMovimiento() * -1L);
        if (movimiento.getId() == null) {
            return construirNuevoMovimiento(movimiento);
        }
        return construirReversionMovimiento(movimiento);
    }

    private Movimiento construirNuevoMovimiento(Movimiento movimiento) {
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());
        validarSaldoDisponibleEnCuenta(movimiento);
        movimiento.setSaldo(cuentaConsultada.getSaldoInicial() + movimiento.getValorMovimiento());
        movimiento.setSaldoAnterior(cuentaConsultada.getSaldoInicial());
        movimiento.getCuenta().setSaldoInicial(movimiento.getSaldo());
        return movimiento;

    }

    private Movimiento construirReversionMovimiento(Movimiento movimiento) {
        validarSaldoDisponibleEnCuentaAlRevertir(movimiento);
        movimiento.setSaldo(movimiento.getSaldoAnterior() + movimiento.getValorMovimiento());
        movimiento.getCuenta().setSaldoInicial(movimiento.getSaldo());
        return movimiento;
    }

    private void validarSaldoDisponibleEnCuenta(Movimiento movimiento) {
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());

        if (cuentaConsultada.getSaldoInicial() + movimiento.getValorMovimiento() < 0L) {
            throw new BusinessException(BusinessException.Type.SALDO_INFERIOR_CERO);
        }
    }

    private void validarSaldoDisponibleEnCuentaAlRevertir(Movimiento movimiento) {

        if (movimiento.getSaldoAnterior() + movimiento.getValorMovimiento() < 0L) {
            throw new BusinessException(BusinessException.Type.SALDO_INFERIOR_CERO);
        }
    }
}
