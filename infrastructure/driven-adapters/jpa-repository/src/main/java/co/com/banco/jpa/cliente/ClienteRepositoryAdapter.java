package co.com.banco.jpa.cliente;

import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClienteRepositoryAdapter extends AdapterOperations<Cliente, ClienteData, Integer, ClienteDataRepository>
        implements ClienteRepository {

    public ClienteRepositoryAdapter(ClienteDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Cliente.ClienteBuilder.class).build());
    }

    @Override
    public List<Cliente> encontrarClientes() {
        return null;
    }
}
