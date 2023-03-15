package co.com.banco.movimiento;

import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.movimiento.dto.MovimientoDTO;

import java.util.ArrayList;
import java.util.List;

public class MovimientoConverter {
    private MovimientoConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static MovimientoDTO movimientoAMovimientoDTO(Movimiento movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(movimiento.getId());
        movimientoDTO.setFecha(movimiento.getFecha());
        movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoDTO.setValorMovimiento(movimiento.getValorMovimiento());
        movimientoDTO.setSaldo(movimiento.getSaldo());

        return movimientoDTO;
    }

    public static List<MovimientoDTO> listMovimientosAListMovimientosDTO(List<Movimiento> listaMovimientos) {
        List<MovimientoDTO> listaMovimientosDTO = new ArrayList<>();
        listaMovimientos.forEach(
                movimiento -> listaMovimientosDTO.add(
                        movimientoAMovimientoDTO(movimiento)
                )
        );

        return listaMovimientosDTO;
    }
}
