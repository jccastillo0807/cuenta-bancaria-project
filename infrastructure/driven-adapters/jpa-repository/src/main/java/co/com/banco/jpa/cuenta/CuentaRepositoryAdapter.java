package co.com.banco.jpa.cuenta;

import co.com.banco.jpa.DataMapper;
import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class CuentaRepositoryAdapter extends AdapterOperations<Cuenta, CuentaData, Integer, CuentaDataRepository>
        implements CuentaRepository {

    @Autowired
    public CuentaRepositoryAdapter(CuentaDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Cuenta.CuentaBuilder.class).build());
    }

    @Override
    public List<Cuenta> encontrarCuentas() {
        return DataMapper.converitirListaCuentasDataAListaCuentas(repository.findAll());
    }

    @Override
    public Cuenta encontrarCuentaPorId(Integer id) {
        CuentaData cuentaData = repository.findById(id).orElse(null);
        if (Objects.isNull(cuentaData)) {
            return null;
        }
        return DataMapper.convertirCuentaDataACuenta(cuentaData);
    }

    @Override
    public Cuenta guardarCuenta(Cuenta cuenta) {
        CuentaData cuentaData = DataMapper.convertirCuentaACuentaData(cuenta);
        return DataMapper.convertirCuentaDataACuenta(repository.save(cuentaData));
    }
}
