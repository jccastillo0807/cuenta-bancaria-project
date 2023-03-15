package co.com.banco.jpa.movimiento;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface MovimientoDataRepository extends CrudRepository<MovimientoData, Integer>, QueryByExampleExecutor<MovimientoData> {
}
