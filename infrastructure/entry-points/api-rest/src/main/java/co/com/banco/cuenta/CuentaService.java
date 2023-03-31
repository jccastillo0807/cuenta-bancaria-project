package co.com.banco.cuenta;

import co.com.banco.cuenta.dto.CuentaDTO;
import co.com.banco.usecase.cuenta.CuentaUseCase;
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
        return convertirCuentaACuentaDTO(cuentaUseCase.obtenerCuentaPor(id));
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public CuentaDTO crearCuenta(@Valid @RequestBody CuentaDTO cuenta) {
        return convertirCuentaACuentaDTO(
                cuentaUseCase.guardar(convertirCuentaDTOACuenta(cuenta)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarCuenta(@PathVariable Integer id) {
        cuentaUseCase.eliminar(id);
    }


    @Transactional
    @PutMapping("/{id}")
    public CuentaDTO editarCuenta(@Valid @RequestBody CuentaDTO cuenta, @PathVariable Integer id) {
        return convertirCuentaACuentaDTO(
                cuentaUseCase.actualizar(id, convertirCuentaDTOACuenta(cuenta)));
    }
}
