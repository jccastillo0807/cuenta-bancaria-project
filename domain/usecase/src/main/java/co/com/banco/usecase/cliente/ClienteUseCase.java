package co.com.banco.usecase.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.persona.Persona;
import co.com.banco.usecase.persona.PersonaUseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static co.com.banco.model.common.ValidationUtils.validarCamposCliente;
import static co.com.banco.model.common.ValidationUtils.validarIdNulo;

@RequiredArgsConstructor
public class ClienteUseCase {

    public static final String ESTADO_INACTIVO = "INACTIVO";
    public static final String ESTADO_ACTIVO = "ACTIVO";
    private final ClienteRepository clienteRepository;
    private final PersonaUseCase personaUseCase;

    public List<Cliente> obtenerClientes() {
        return clienteRepository.encontrarClientes(ESTADO_ACTIVO);
    }

    public Cliente obtenerClientePorId(Integer id) {
        validarIdNulo(id);
        Cliente clienteEncontrado = clienteRepository.encontrarPorId(id);
        if (Objects.isNull(clienteEncontrado) || !clienteEncontrado.getEstado().equalsIgnoreCase(ESTADO_ACTIVO)) {
            throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
        }
        return clienteEncontrado;
    }

    public Cliente guardarCliente(Cliente cliente) {
        validarCamposCliente(cliente);
        Persona personaEncontrada = personaUseCase.encontrarPorTipoYNumeroDocumento(
                cliente.getPersona().getTipoDocumento(), cliente.getPersona().getNumeroDocumento()
        );
        if (Objects.nonNull(personaEncontrada)) {
            throw new BusinessException(BusinessException.Type.PERSONA_EXISTE);
        }
        Persona personaCreada = personaUseCase.guardarPersona(cliente.getPersona());
        cliente.setPersona(personaCreada);
        return clienteRepository.guardarCliente(cliente);
    }


    public void inactivarCliente(Integer id) {
        Cliente clienteEncontrado = obtenerClientePorId(id);
        clienteEncontrado.setEstado(ESTADO_INACTIVO);
        clienteRepository.guardarCliente(clienteEncontrado);
    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) {
        validarCamposCliente(cliente);
        Persona personaEnBaseDatos = personaUseCase.encontrarPorTipoYNumeroDocumento(
                cliente.getPersona().getTipoDocumento(), cliente.getPersona().getNumeroDocumento());
        if (Objects.isNull(personaEnBaseDatos)) {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
        Persona personaActualizada = personaUseCase.editarPersona( cliente, personaEnBaseDatos);
        Cliente clienteEnBaseDeDatos = obtenerClientePorId(id);
        clienteEnBaseDeDatos.setPassword(cliente.getPassword());
        clienteEnBaseDeDatos.setPersona(personaActualizada);
        return clienteRepository.guardarCliente(clienteEnBaseDeDatos);
    }

}
