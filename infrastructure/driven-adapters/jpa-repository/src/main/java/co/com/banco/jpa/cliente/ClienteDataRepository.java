package co.com.banco.jpa.cliente;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ClienteDataRepository extends CrudRepository<ClienteData, Integer>, QueryByExampleExecutor<ClienteData> {
}
