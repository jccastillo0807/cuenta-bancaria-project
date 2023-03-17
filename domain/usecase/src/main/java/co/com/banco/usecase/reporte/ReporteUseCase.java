package co.com.banco.usecase.reporte;

import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.movimiento.gateways.MovimientoRepository;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
public class ReporteUseCase {

    private final MovimientoRepository movimientoRepository;

    public List<Movimiento> generarReporteEntreFechas(String  inicio, String fin) {
        if (inicio != null && fin != null) {
            Date fechaInicio = convertirFechaStringADate(inicio);
            Date fechaFinal= convertirFechaStringADate(fin);
            if (fechaInicio.after(fechaFinal)) {
                throw new BusinessException(BusinessException.Type.FECHA_INICIAL_MAYOR);
            }
            return movimientoRepository.generarReporteEntreFechas(fechaInicio, fechaFinal);
        }
        throw new BusinessException(BusinessException.Type.FECHA_PARAMETRO_NO_ENCONTRADO);
    }

    private Date convertirFechaStringADate(String fechaString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date fechaDate;
        try {
            fechaDate = formatter.parse(fechaString);
        } catch (ParseException e) {
            throw new BusinessException(BusinessException.Type.FORMATO_FECHA_INVALID);
        }
        return fechaDate;
    }
}
