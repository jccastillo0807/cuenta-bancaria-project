package co.com.banco.model.cliente.gateways;

import co.com.banco.model.cliente.Cliente;

import java.util.List;

public interface ClienteRepository {
    List<Cliente> encontrarClientes(String estado);

    Cliente encontrarPorId(Integer id);

    Cliente guardarCliente(Cliente cliente);
}
