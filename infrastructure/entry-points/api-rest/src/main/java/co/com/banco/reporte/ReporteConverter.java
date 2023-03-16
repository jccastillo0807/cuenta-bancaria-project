package co.com.banco.reporte;

import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.reporte.dto.ReporteDTO;

import java.util.ArrayList;
import java.util.List;

public class ReporteConverter {
    public ReporteConverter() {
        throw new IllegalStateException("Utility class");
    }

    private static ReporteDTO movimientoAReporteDTO(Movimiento movimiento) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setIdMovimiento(movimiento.getId());
        reporteDTO.setFechaMovimiento(movimiento.getFechaMovimiento());
        reporteDTO.setCliente(movimiento.getCuenta().getCliente().getPersona().getNombre() + " " +
                movimiento.getCuenta().getCliente().getPersona().getApellido());
        reporteDTO.setTipo(movimiento.getCuenta().getTipoCuenta());
        reporteDTO.setSaldoInicial(movimiento.getSaldo());
        reporteDTO.setEstado(movimiento.getCuenta().getEstado());
        reporteDTO.setSaldoDisponible(movimiento.getCuenta().getSaldoInicial());

        return reporteDTO;
    }


    public static List<ReporteDTO> listMovimientoAListReporteDTO(List<Movimiento> listaMovimientos){
        List<ReporteDTO> listaReporteDTO = new ArrayList<>();
        listaMovimientos.forEach(
                movimiento -> listaReporteDTO.add(
                        movimientoAReporteDTO(movimiento)
                )
        );        
        return listaReporteDTO;
    }

}
