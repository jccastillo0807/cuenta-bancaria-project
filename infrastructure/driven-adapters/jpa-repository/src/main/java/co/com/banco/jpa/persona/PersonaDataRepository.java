package co.com.banco.jpa.persona;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface PersonaDataRepository extends CrudRepository<PersonaData, Integer>, QueryByExampleExecutor<PersonaData> {
    PersonaData findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);
}
