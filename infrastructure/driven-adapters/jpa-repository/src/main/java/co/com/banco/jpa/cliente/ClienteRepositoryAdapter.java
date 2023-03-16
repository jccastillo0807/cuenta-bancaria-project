package co.com.banco.jpa.cliente;

import co.com.banco.jpa.DataMapper;
import co.com.banco.jpa.helper.AdapterOperations;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ClienteRepositoryAdapter extends AdapterOperations<Cliente, ClienteData, Integer, ClienteDataRepository>
        implements ClienteRepository {

    @Autowired
    public ClienteRepositoryAdapter(ClienteDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Cliente.ClienteBuilder.class).build());
    }

    @Override
    public List<Cliente> encontrarClientes() {
        return DataMapper.convertirlistClienteDataAListCliente(repository.findAll());
    }

    @Override
    public Cliente encontrarPorId(Integer id) {
        ClienteData clienteData = repository.findById(id).orElse(null);
        if (Objects.isNull(clienteData)) {
            return null;
        }
        return DataMapper.convertirClienteDataACliente(clienteData);
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        ClienteData clienteData = DataMapper.convertirClienteAClienteData(cliente);
        return DataMapper.convertirClienteDataACliente(repository.save(clienteData));
    }
}
