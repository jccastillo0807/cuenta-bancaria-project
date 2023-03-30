package co.com.banco.usecase.persona;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;


@RequiredArgsConstructor
public class PersonaUseCase {

    private final PersonaRepository personaRepository;

    public Persona encontrarPorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        Persona personaEncontrada = personaRepository.encontrarPorTipoYNumeroDocumento(tipoDocumento, numeroDocumento);
        if (Objects.isNull(personaEncontrada)) {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
        return personaEncontrada;
    }

    public Persona guardarPersonaEditada(Persona persona) {
        return personaRepository.save(persona);
    }

    public Persona editarPersona(Cliente cliente, Persona persona) {
        if (cliente.getPersona().getId() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }
        persona.setNombre(cliente.getPersona().getNombre());
        persona.setApellido(cliente.getPersona().getApellido());
        persona.setDireccion(cliente.getPersona().getDireccion());
        persona.setTelefono(cliente.getPersona().getTelefono());
        return guardarPersonaEditada(persona);
    }

}
