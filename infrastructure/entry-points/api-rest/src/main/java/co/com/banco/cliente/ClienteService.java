package co.com.banco.cliente;

import co.com.banco.cliente.dto.ClienteDTO;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.usecase.cliente.ClienteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static co.com.banco.DTOMapper.convertirClienteAClienteDTO;
import static co.com.banco.DTOMapper.listClienteAListaClienteDTO;

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
    public List<ClienteDTO> verTodosLosClientes() {
        return listClienteAListaClienteDTO(clienteUseCase.obtenerClientes());
    }


    @GetMapping("/{id}")
    public ClienteDTO encontrarClientePorId(@PathVariable Integer id) {

        return convertirClienteAClienteDTO(clienteUseCase.obtenerClientePorId(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClienteDTO crearCliente(@RequestBody Cliente cliente) {
        return convertirClienteAClienteDTO(clienteUseCase.guardarCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCliente(@PathVariable Integer id) {
        clienteUseCase.inactivarCliente(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO editarCliente(@RequestBody Cliente cliente, @PathVariable Integer id) {
        return convertirClienteAClienteDTO(clienteUseCase.actualizarCliente(id, cliente));
    }

}
