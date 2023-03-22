package co.com.banco.jpa.cliente;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface ClienteDataRepository extends CrudRepository<ClienteData, Integer>, QueryByExampleExecutor<ClienteData> {
    List<ClienteData> findByEstado(String estado);
}
