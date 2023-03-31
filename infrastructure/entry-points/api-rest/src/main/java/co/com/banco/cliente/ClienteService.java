package co.com.banco.cliente;

import co.com.banco.cliente.dto.ClienteDTO;
import co.com.banco.usecase.cliente.ClienteUseCase;
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

        return convertirClienteAClienteDTO(clienteUseCase.obtenerPor(id));
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClienteDTO crearCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return convertirClienteAClienteDTO(
                clienteUseCase.guardar(convertirClienteDTOACliente(clienteDTO)));
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarCliente(@PathVariable Integer id) {
        clienteUseCase.inactivarPor(id);
    }

    @Transactional
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO editarCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
        return convertirClienteAClienteDTO(
                clienteUseCase.actualizar(id, convertirClienteDTOACliente(clienteDTO)));
    }

}
