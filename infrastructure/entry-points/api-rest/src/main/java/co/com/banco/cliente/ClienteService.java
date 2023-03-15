package co.com.banco.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.usecase.cliente.ClienteUseCase;
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
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteUseCase clienteUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> verTodosLosClientes() {
        return clienteUseCase.obtenerClientes();
    }


    @GetMapping("/{id}")
    public Cliente encontrarClientePorId(@PathVariable Integer id) {
        return clienteUseCase.obtenerClientePorId(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteUseCase.guardarCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Integer id) {
        clienteUseCase.inactivarCliente(id);
    }

    @PutMapping("/{id}")
    public Cliente editarCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {
        return clienteUseCase.actualizarCliente(id, cliente);
    }
}
