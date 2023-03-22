package co.com.banco.jpa.persona;

import co.com.banco.jpa.DataMapper;
import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class PersonaRepositoryAdapter extends AdapterOperations<Persona, PersonaData, Integer, PersonaDataRepository>
        implements PersonaRepository {

    @Autowired
    public PersonaRepositoryAdapter(PersonaDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Persona.PersonaBuilder.class).build());
    }

    @Override
    public Persona encontrarPersonaPorId(Integer id) {
        PersonaData personaData = repository.findById(id).orElse(null);
        if (Objects.isNull(personaData)) {
            return null;
        }
        return DataMapper.convertirPersonaDataAPersona(personaData);
    }

    @Override
    public Persona encontrarPorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        PersonaData personaData =  repository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
        if (Objects.isNull(personaData)) {
            return null;
        }
        return DataMapper.convertirPersonaDataAPersona(personaData);
    }
}
