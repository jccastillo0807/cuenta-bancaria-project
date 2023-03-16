package co.com.banco.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.usecase.cliente.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(
        origins = {"http://localhost:4200"},
        methods = {RequestMethod.GET, RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteService {

    public static final String MENSAJE = "mensaje";
    public static final String MENSAJE_ERROR_EN_BASE_DE_DATOS = "Error al realizar la consulta en la base de datos";
    public static final String ERROR = "error";
    public static final String CLIENTE_CON_ID = "El cliente con ID: ";
    public static final String NO_ENCONTRADO_EN_BD = " no se encuentra registrado en la base de datos!";
    public static final String MENSAJE_ERROR_ACTUALIZAR_CLIENTE = "No se pudo EDITAR, el cliente ID: ";
    public static final String EXITO_CLIENTE_ACTUALIZADO = "El cliente ha sido actualizado con éxito!";
    public static final String CLIENTE = "cliente";
    public static final String MENSAJE_ERROR_CREAR_CLIENTE = "Error al crear CLIENTE en la base de datos";
    public static final String EXITO_CLIENTE_CREADO = "El cliente ha sido CREADO con éxito!";
    public static final String MENSAJE_ERROR_ELIMINAR_CLIENTE = "Error al eliminar el CLIENTE de la base de datos";
    public static final String EXITO_CLIENTE_ELIMINADO = "Cliente ELIMINADO con éxito!";
    private final ClienteUseCase clienteUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> verTodosLosClientes() {
        return clienteUseCase.obtenerClientes();
    }


    /*@GetMapping("/{id}")
    public Cliente encontrarClientePorId(@PathVariable Integer id) {
        return clienteUseCase.obtenerClientePorId(id);
    }*/
    @GetMapping("/{id}")
    public ResponseEntity<?> encontrarClientePorId(@PathVariable Integer id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteUseCase.obtenerClientePorId(id);
        } catch (DataAccessException e) {

            response.put(MENSAJE, MENSAJE_ERROR_EN_BASE_DE_DATOS);
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put(MENSAJE, CLIENTE_CON_ID.concat(id.toString().concat(NO_ENCONTRADO_EN_BD)));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    /*@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteUseCase.guardarCliente(cliente);
    }*/

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
        Cliente clienteCreado = null;
        Map<String, Object> response = new HashMap<>();

//        if(result.hasErrors()) {
//
//            List<String> errors = result.getFieldErrors()
//                    .stream()
//                    .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
//                    .collect(Collectors.toList());
//
//            response.put("errors", errors);
//            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
//        }

        try {
            clienteCreado = clienteUseCase.guardarCliente(cliente);
        } catch (DataAccessException e) {
            response.put(MENSAJE, MENSAJE_ERROR_CREAR_CLIENTE);
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put(MENSAJE, EXITO_CLIENTE_CREADO);
        response.put("cliente", clienteCreado);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCliente(@PathVariable Integer id) {
        clienteUseCase.inactivarCliente(id);
    }*/
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Integer id) {
        Cliente clienteInactivo = null;
        Map<String, Object> response = new HashMap<>();

        try {
            clienteInactivo = clienteUseCase.inactivarCliente(id);
        } catch (DataAccessException e) {
            response.put(MENSAJE, MENSAJE_ERROR_ELIMINAR_CLIENTE);
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (clienteInactivo == null) {
            response.put(MENSAJE, CLIENTE_CON_ID.concat(id.toString().concat(NO_ENCONTRADO_EN_BD)));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        response.put(MENSAJE, EXITO_CLIENTE_ELIMINADO);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    /*@PutMapping("/{id}")
    public Cliente editarCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {
        return clienteUseCase.actualizarCliente(id, cliente);
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {

        Cliente clienteActualizado = null;

        Map<String, Object> response = new HashMap<>();

//        if(result.hasErrors()) {
//
//            List<String> errors = result.getFieldErrors()
//                    .stream()
//                    .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
//                    .collect(Collectors.toList());
//
//            response.put("errors", errors);
//            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
//        }


        try {
            clienteActualizado = clienteUseCase.actualizarCliente(id, cliente);
            if (clienteActualizado == null) {
                response.put(MENSAJE, MENSAJE_ERROR_ACTUALIZAR_CLIENTE
                        .concat(id.toString().concat(NO_ENCONTRADO_EN_BD)));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }

        } catch (DataAccessException e) {
            response.put(MENSAJE, MENSAJE_ERROR_ACTUALIZAR_CLIENTE);
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put(MENSAJE, EXITO_CLIENTE_ACTUALIZADO);
        response.put(CLIENTE, clienteActualizado);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}
