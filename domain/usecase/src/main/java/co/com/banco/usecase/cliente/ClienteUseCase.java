package co.com.banco.usecase.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
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
    private final PersonaRepository personaRepository;

    public List<Cliente> obtenerClientes() {
        return clienteRepository.encontrarClientes(ESTADO_ACTIVO);
    }

    public Cliente obtenerClientePorId(Integer id) {
        validarIdNulo(id);
        Cliente clienteEncontrado = clienteRepository.encontrarPorId(id);
        if (Objects.isNull(clienteEncontrado)) {
            throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
        }
        return clienteEncontrado;
    }

    public Cliente guardarCliente(Cliente cliente) {
        validarCamposCliente(cliente);
        Persona personaEncontrada = personaRepository.encontrarPorTipoYNumeroDocumento(
                cliente.getPersona().getTipoDocumento(), cliente.getPersona().getNumeroDocumento()
        );

        if (Objects.isNull(personaEncontrada)) {
            Persona personaCreada = personaRepository.save(cliente.getPersona());
            cliente.setPersona(personaCreada);
            return clienteRepository.guardarCliente(cliente);
        }
        throw new BusinessException(BusinessException.Type.PERSONA_EXISTE);
    }


    public void inactivarCliente(Integer id) {
        Cliente clienteEncontrado = clienteRepository.encontrarPorId(id);
        if (Objects.isNull(clienteEncontrado)) {
            throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
        }
        clienteEncontrado.setEstado(ESTADO_INACTIVO);
        clienteRepository.guardarCliente(clienteEncontrado);

    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) {

        validarCamposCliente(cliente);
        validarSiExistePersona(cliente);
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

    private void validarSiExistePersona(Cliente cliente) {
        if (cliente.getPersona().getId() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }

        Persona existePersona = personaRepository.encontrarPersonaPorId(cliente.getPersona().getId());
        if (Objects.isNull(existePersona)) {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
    }


}
