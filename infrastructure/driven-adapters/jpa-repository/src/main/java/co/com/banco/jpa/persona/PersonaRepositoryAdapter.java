package co.com.banco.jpa.persona;

import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepositoryAdapter extends AdapterOperations<Persona, PersonaData, Integer, PersonaDataRepository>
        implements PersonaRepository {

    @Autowired
    public PersonaRepositoryAdapter(PersonaDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Persona.PersonaBuilder.class).build());
    }
}
