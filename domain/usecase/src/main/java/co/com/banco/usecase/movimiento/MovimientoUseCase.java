package co.com.banco.usecase.movimiento;

import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.movimiento.gateways.MovimientoRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MovimientoUseCase {

    private final MovimientoRepository movimientoRepository;

    public List<Movimiento> obtenerMovimientos() {
        return movimientoRepository.verMovimientos();
    }

    public Movimiento findById(Integer id) {
        return movimientoRepository.encontrarPorId(id);
    }
}
