package co.com.banco.model.movimiento.gateways;

import co.com.banco.model.movimiento.Movimiento;

import java.util.List;

public interface MovimientoRepository {
    List<Movimiento> findAll();
    List<Movimiento> verMovimientos();

    Movimiento encontrarPorId(Integer id);
}
