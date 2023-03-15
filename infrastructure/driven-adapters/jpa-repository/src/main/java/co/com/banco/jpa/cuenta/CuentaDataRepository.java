package co.com.banco.jpa.cuenta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface CuentaDataRepository extends CrudRepository<CuentaData, Integer>, QueryByExampleExecutor<CuentaData> {
}
