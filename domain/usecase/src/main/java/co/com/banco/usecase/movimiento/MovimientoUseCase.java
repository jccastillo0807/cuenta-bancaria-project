package co.com.banco.usecase.movimiento;

import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.movimiento.gateways.MovimientoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MovimientoUseCase {

    public static final String DEBITO = "DEBITO";
    private final MovimientoRepository movimientoRepository;

    private final CuentaRepository cuentaRepository;

    public List<Movimiento> obtenerMovimientos() {
        return movimientoRepository.verMovimientos();
    }

    public Movimiento findById(Integer id) {
        return movimientoRepository.encontrarPorId(id);
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        Movimiento movimientoAplicado = aplicarMovimiento(movimiento);
        Cuenta cuentaConNuevoSaldo = aplicarNuevoSaldoEnCuenta(movimientoAplicado.getCuenta());
        movimientoAplicado.setCuenta(cuentaConNuevoSaldo);
        return movimientoRepository.crearMovimiento(movimientoAplicado);
    }

    private Movimiento aplicarMovimiento(Movimiento movimiento) {
        Movimiento movimientoAplicado = movimiento;
        Cuenta cuentaConsultada = cuentaRepository.encontrarCuentaPorId(movimiento.getCuenta().getId());
        if (movimiento.getTipoMovimiento().toUpperCase().equalsIgnoreCase(DEBITO)) {
            Long saldoDisponible = validarSaldoDisponibleEnCuenta(movimiento.getCuenta().getId(), movimiento.getValorMovimiento());
            if (saldoDisponible > 0L) {
                movimientoAplicado.setSaldo(cuentaConsultada.getSaldoInicial() - movimiento.getValorMovimiento());
                movimientoAplicado.getCuenta().setSaldoInicial(movimientoAplicado.getSaldo());
                movimientoAplicado.setValorMovimiento(movimiento.getValorMovimiento() * -1L);
            }else {
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
}
