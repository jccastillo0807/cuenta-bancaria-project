package co.com.banco.cuenta;

import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.usecase.cuenta.CuentaUseCase;
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
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaService {
    private final CuentaUseCase cuentaUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cuenta> verTodosLasCuentas() {
        return cuentaUseCase.obtenerCuentas();
    }


    @GetMapping("/{id}")
    public Cuenta encontrarCuentaPorId(@PathVariable Integer id) {
        return cuentaUseCase.obtenerCuentaPorId(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Cuenta crearCuenta(@RequestBody Cuenta cuenta) {
        return cuentaUseCase.guardarCuenta(cuenta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarCuenta(@PathVariable Integer id) {
        cuentaUseCase.eliminarCuenta(id);
    }

    @PutMapping("/{id}")
    public Cuenta editarCuenta(@RequestBody Cuenta cuenta, @PathVariable Integer id) {
        return cuentaUseCase.actualizarCuenta(id, cuenta);
    }
}
