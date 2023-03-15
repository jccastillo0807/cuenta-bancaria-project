package co.com.banco.jpa.cuenta;

import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CuentaRepositoryAdapter extends AdapterOperations<Cuenta, CuentaData, Integer, CuentaDataRepository>
        implements CuentaRepository {

    @Autowired
    public CuentaRepositoryAdapter(CuentaDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Cuenta.CuentaBuilder.class).build());
    }
}
