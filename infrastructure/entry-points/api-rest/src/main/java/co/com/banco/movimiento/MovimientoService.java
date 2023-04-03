package co.com.banco.movimiento;

import co.com.banco.movimiento.dto.MovimientoDTO;
import co.com.banco.usecase.movimiento.MovimientoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static co.com.banco.DTOMapper.*;

@CrossOrigin(
        origins = {"http://localhost:4200"},
        methods = {RequestMethod.GET, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoUseCase movimientoUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MovimientoDTO> verTodosLosMovimientos() {
        return listMovimientosAListMovimientosDTO(movimientoUseCase.obtenerMovimientos());
    }

    @GetMapping("/{id}")
    public MovimientoDTO encontrarPorId(@PathVariable Integer id) {
        return movimientoAMovimientoDTO(movimientoUseCase.encontrarPorId(id));
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public MovimientoDTO crearMovimiento(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        return movimientoAMovimientoDTO(
                movimientoUseCase.guardarMovimiento(movimientoDTOAMovimiento(movimientoDTO))
        );
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarMovimiento(@PathVariable Integer id) {
        movimientoUseCase.eliminarMovimiento(id);
    }

    @Transactional
    @PutMapping("/{id}")
    public MovimientoDTO editarCliente(@Valid @RequestBody MovimientoDTO movimientoDTO, @PathVariable Integer id) {
        return movimientoAMovimientoDTO(
                movimientoUseCase.actualizarMovimiento(id, movimientoDTOAMovimiento(movimientoDTO))
        );
    }
}
