package co.com.banco.jpa.movimiento;

import co.com.banco.jpa.DataMapper;
import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.movimiento.gateways.MovimientoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovimientoRepositoryAdapter extends AdapterOperations<Movimiento, MovimientoData, Integer, MovimientoDataRepository>
implements MovimientoRepository {

    @Autowired
    public MovimientoRepositoryAdapter(MovimientoDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Movimiento.MovimientoBuilder.class).build());
    }

    @Override
    public List<Movimiento> verMovimientos() {
        return DataMapper.converitirListaMovimientosDataAListaMovimiento(repository.findAll());
    }

    @Override
    public Movimiento encontrarPorId(Integer id) {
        return DataMapper.convertirMovimientoDataAMovimiento(
                repository.findById(id).get()
        );
    }
}
