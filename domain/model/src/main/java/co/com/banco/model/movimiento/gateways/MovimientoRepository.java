package co.com.banco.model.movimiento.gateways;

import co.com.banco.model.movimiento.Movimiento;

import java.util.Date;
import java.util.List;

public interface MovimientoRepository {
    List<Movimiento> findAll();

    List<Movimiento> verMovimientos();

    Movimiento encontrarPorId(Integer id);

    Movimiento crearMovimiento(Movimiento movimiento);

    List<Movimiento> generarReporteEntreFechas(Date inicio, Date fin);

    void deleteById(Integer id);

    List<Movimiento> encontrarMovimientosPorCuentaAsociada(Integer id);
}
