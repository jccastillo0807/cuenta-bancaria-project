package co.com.banco.usecase.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class ClienteUseCase {

    public static final String ESTADO_INACTIVO = "INACTIVO";
    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    public List<Cliente> obtenerClientes() {
       return clienteRepository.encontrarClientes();
    }

    public Cliente obtenerClientePorId(Integer id) {
        return clienteRepository.encontrarPorId(id);
    }

    public Cliente guardarCliente(Cliente cliente) {
       Persona personaCreada = personaRepository.save(cliente.getPersona());
       cliente.setPersona(personaCreada);
       return clienteRepository.guardarCliente(cliente);
    }

    public Cliente inactivarCliente(Integer id) {
        Cliente cliente = clienteRepository.encontrarPorId(id);

        if (Objects.isNull(cliente)){
            return null;
        }
        cliente.setEstado(ESTADO_INACTIVO);
        return clienteRepository.guardarCliente(cliente);

    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) {

        Cliente clienteEnBaseDeDatos = clienteRepository.encontrarPorId(id);
        Persona personaEnBaseDeDatos = personaRepository.findById(cliente.getPersona().getId());

        if (Objects.isNull(clienteEnBaseDeDatos)){
            return null;
        }
        clienteEnBaseDeDatos.setPassword(cliente.getPassword());
        personaEnBaseDeDatos.setNombre(cliente.getPersona().getNombre());
        personaEnBaseDeDatos.setApellido(cliente.getPersona().getApellido());
        personaEnBaseDeDatos.setDireccion(cliente.getPersona().getDireccion());
        personaEnBaseDeDatos.setTelefono(cliente.getPersona().getTelefono());

        personaRepository.save(personaEnBaseDeDatos);
        return clienteRepository.guardarCliente(clienteEnBaseDeDatos);
    }
}
