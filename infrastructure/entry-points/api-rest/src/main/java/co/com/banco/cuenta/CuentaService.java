package co.com.banco.cuenta;

import co.com.banco.cuenta.dto.CuentaDTO;
import co.com.banco.usecase.cuenta.CuentaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.com.banco.DTOMapper.*;

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
    public List<CuentaDTO> verTodosLasCuentas() {
        return listCuentaAListaCuentaDTO(cuentaUseCase.obtenerCuentas());
    }


    @GetMapping("/{id}")
    public CuentaDTO encontrarCuentaPorId(@PathVariable Integer id) {
        return convertirCuentaACuentaDTO(cuentaUseCase.obtenerCuentaPorId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public CuentaDTO crearCuenta(@RequestBody CuentaDTO cuenta) {
        return convertirCuentaACuentaDTO(cuentaUseCase.guardarCuenta(convertirCuentaDTOACuenta(cuenta)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarCuenta(@PathVariable Integer id) {
        cuentaUseCase.eliminarCuenta(id);
    }

    @PutMapping("/{id}")
    public CuentaDTO editarCuenta(@RequestBody CuentaDTO cuenta, @PathVariable Integer id) {
        return convertirCuentaACuentaDTO(cuentaUseCase.actualizarCuenta(id, convertirCuentaDTOACuenta(cuenta)));
    }
}
