package co.com.banco.usecase.movimiento;

import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import co.com.banco.model.movimiento.Movimiento;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovimientoCreditoUseCase {

    private final CuentaRepository cuentaRepository;

    public Movimiento construirMovimientoCredito(Movimiento movimiento) {

        if (movimiento.getId() == null) {
            return construirNuevoMovimiento(movimiento);
        }
        return construirReversionMovimiento(movimiento);
    }

    private Movimiento construirReversionMovimiento(Movimiento movimiento) {
        movimiento.setSaldo(movimiento.getSaldoAnterior() + movimiento.getValorMovimiento());
        movimiento.getCuenta().setSaldoInicial(movimiento.getSaldo());
        return movimiento;
    }

    private Movimiento construirNuevoMovimiento(Movimiento movimiento) {
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());
        movimiento.setSaldo(cuentaConsultada.getSaldoInicial() + movimiento.getValorMovimiento());
        movimiento.setSaldoAnterior(cuentaConsultada.getSaldoInicial());
        movimiento.getCuenta().setSaldoInicial(movimiento.getSaldo());
        return movimiento;
    }
}
