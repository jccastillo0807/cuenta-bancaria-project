package co.com.banco.model.persona.gateways;

import co.com.banco.model.persona.Persona;

public interface PersonaRepository {
    Persona save(Persona persona);
    Persona encontrarPersonaPorId(Integer id);

}
