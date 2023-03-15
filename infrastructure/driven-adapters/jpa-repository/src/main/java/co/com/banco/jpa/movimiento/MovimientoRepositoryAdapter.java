package co.com.banco.jpa.movimiento;

import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.movimiento.gateways.MovimientoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovimientoRepositoryAdapter extends AdapterOperations<Movimiento, MovimientoData, Integer, MovimientoDataRepository>
implements MovimientoRepository {

    @Autowired
    public MovimientoRepositoryAdapter(MovimientoDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Movimiento.MovimientoBuilder.class).build());
    }
}
