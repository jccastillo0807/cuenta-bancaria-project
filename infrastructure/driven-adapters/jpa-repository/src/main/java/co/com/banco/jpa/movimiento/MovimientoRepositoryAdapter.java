package co.com.banco.jpa.movimiento;

import co.com.banco.jpa.DataMapper;
import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.movimiento.gateways.MovimientoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        MovimientoData movimientoData = repository.findById(id).orElse(null);
        if (Objects.isNull(movimientoData)) {
            return null;
        }
        return DataMapper.convertirMovimientoDataAMovimiento(movimientoData);
    }

    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        MovimientoData movimientoData = DataMapper.convertirMovimientoAMovimientoData(movimiento);
        return DataMapper.convertirMovimientoDataAMovimiento(repository.save(movimientoData));
    }

    @Override
    public List<Movimiento> generarReporteEntreFechas(Date inicio, Date fin) {
        return DataMapper.converitirListaMovimientosDataAListaMovimiento(repository.findByFechaMovimientoBetween(inicio, fin));
    }

    @Override
    public void deleteById(Integer id) {
         repository.deleteById(id);
    }
}
