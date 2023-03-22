package co.com.banco.jpa.cuenta;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface CuentaDataRepository extends CrudRepository<CuentaData, Integer>, QueryByExampleExecutor<CuentaData> {
    CuentaData findByNumeroCuenta(String numeroCuenta);
    List<CuentaData> findByEstado(String estado);
}
