package co.com.banco.movimiento;

import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.usecase.movimiento.MovimientoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Movimiento> verTodos() {
        return movimientoUseCase.obtenerMovimientos();
    }

    @GetMapping("/{id}")
    public Movimiento encontrarPorId(@PathVariable Integer id){
        return movimientoUseCase.findById(id);
    }


}
