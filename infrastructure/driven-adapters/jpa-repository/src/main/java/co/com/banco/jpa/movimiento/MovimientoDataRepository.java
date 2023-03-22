package co.com.banco.jpa.movimiento;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Date;
import java.util.List;

public interface MovimientoDataRepository extends CrudRepository<MovimientoData, Integer>, QueryByExampleExecutor<MovimientoData> {
List<MovimientoData> findByFechaMovimientoBetween(Date inicio, Date fin);
}
