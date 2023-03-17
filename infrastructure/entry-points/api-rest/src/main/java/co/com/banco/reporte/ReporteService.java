package co.com.banco.reporte;

import co.com.banco.DTOMapper;
import co.com.banco.reporte.dto.ReporteDTO;
import co.com.banco.usecase.reporte.ReporteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(
        origins = {"http://localhost:4200"},
        methods = {RequestMethod.GET, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteUseCase reporteUseCase;

    @GetMapping
    public List<ReporteDTO> generarReporte(String inicio, String fin) {
        return DTOMapper.listMovimientoAListReporteDTO(
                reporteUseCase.generarReporteEntreFechas(inicio, fin));
    }

}
