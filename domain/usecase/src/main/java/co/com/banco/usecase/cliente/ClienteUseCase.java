package co.com.banco.usecase.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static co.com.banco.model.common.ValidationUtils.validarCliente;

@RequiredArgsConstructor
public class ClienteUseCase {

    public static final String ESTADO_INACTIVO = "INACTIVO";
    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;

    public List<Cliente> obtenerClientes() {
        return clienteRepository.encontrarClientes();
    }

    public Cliente obtenerClientePorId(Integer id) {
        if (id != null) {
            Cliente clienteEncontrado = clienteRepository.encontrarPorId(id);
            if (!Objects.isNull(clienteEncontrado)) {
                return clienteEncontrado;
            } else {
                throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
            }
        }
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    public Cliente guardarCliente(Cliente cliente) {
        Persona personaEncontrada = personaRepository.encontrarPorTipoYNumeroDocumento(cliente.getPersona().getTipoDocumento(), cliente.getPersona().getNumeroDocumento());
        Boolean validarCamposCliente = validarCliente(cliente);

        if (Boolean.TRUE.equals(validarCamposCliente)) {
            if (Objects.isNull(personaEncontrada)){
                Persona personaCreada = personaRepository.save(cliente.getPersona());
                cliente.setPersona(personaCreada);
                return clienteRepository.guardarCliente(cliente);
            }
            throw new BusinessException(BusinessException.Type.PERSONA_EXISTE);
        }

        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }


    public void inactivarCliente(Integer id) {
        if (id != null) {
            Cliente clienteEncontrado = clienteRepository.encontrarPorId(id);
            if (!Objects.isNull(clienteEncontrado)) {
                clienteEncontrado.setEstado(ESTADO_INACTIVO);
                clienteRepository.guardarCliente(clienteEncontrado);
            }
        }
    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) {

        Boolean validarCamposCliente = validarCliente(cliente);
        Boolean validarEntidadPersona = validarSiExistePersona(cliente);
        if (validarEntidadPersona && validarCamposCliente) {
            Cliente clienteEnBaseDeDatos = clienteRepository.encontrarPorId(id);
            Persona personaEnBaseDeDatos = personaRepository.encontrarPersonaPorId(cliente.getPersona().getId());

            if (Objects.isNull(clienteEnBaseDeDatos)) {
                throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
            }
            clienteEnBaseDeDatos.setPassword(cliente.getPassword());
            personaEnBaseDeDatos.setNombre(cliente.getPersona().getNombre());
            personaEnBaseDeDatos.setApellido(cliente.getPersona().getApellido());
            personaEnBaseDeDatos.setDireccion(cliente.getPersona().getDireccion());
            personaEnBaseDeDatos.setTelefono(cliente.getPersona().getTelefono());

            personaRepository.save(personaEnBaseDeDatos);
            return clienteRepository.guardarCliente(clienteEnBaseDeDatos);
        }

        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    private Boolean validarSiExistePersona(Cliente cliente) {
        if (cliente.getPersona().getId() != null) {
            Persona existePersona = personaRepository.encontrarPersonaPorId(cliente.getPersona().getId());
            if (Objects.nonNull(existePersona)) {
                return true;
            } else {
                throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
            }
        }
        throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
    }
}
