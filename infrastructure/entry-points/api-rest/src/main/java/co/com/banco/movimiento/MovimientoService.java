package co.com.banco.movimiento;

import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.usecase.movimiento.MovimientoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public List<Movimiento> verTodosLosMovimientos() {
        return movimientoUseCase.obtenerMovimientos();
    }

    @GetMapping("/{id}")
    public Movimiento encontrarPorId(@PathVariable Integer id) {
        return movimientoUseCase.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Movimiento crearMovimiento(@RequestBody Movimiento movimiento) {
        return movimientoUseCase.guardarMovimiento(movimiento);
    }
/*
*
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Integer id) {
        clienteUseCase.inactivarCliente(id);
    }

    @PutMapping("/{id}")
    public Cliente editarCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {
        return clienteUseCase.actualizarCliente(id, cliente);
    }*/
}
