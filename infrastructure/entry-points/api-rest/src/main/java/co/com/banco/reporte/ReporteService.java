package co.com.banco.reporte;

import co.com.banco.DTOMapper;
import co.com.banco.reporte.dto.ReporteDTO;
import co.com.banco.usecase.movimiento.MovimientoUseCase;
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

    private final MovimientoUseCase movimientoUseCase;

    @GetMapping
    public List<ReporteDTO> encontrar(String inicio, String fin) {
        return DTOMapper.listMovimientoAListReporteDTO(
                movimientoUseCase.generarReporteEntreFechas(inicio, fin));
    }

}
