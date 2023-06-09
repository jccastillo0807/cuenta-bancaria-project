package co.com.banco.usecase.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.persona.Persona;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClienteUseCaseTest {

    @Mock
    ClienteRepository clienteRepository;

    private final Persona persona = Persona.builder()
            .id(1)
            .numeroDocumento("1234567")
            .tipoDocumento("Cedula")
            .nombre("Test_name")
            .apellido("apellido")
            .genero("genero")
            .direccion("direccion")
            .telefono("telefono")
            .build();

    private final Cliente cliente = Cliente.builder()
            .id(1)
            .password("1234")
            .usuario("test")
            .estado("estado_test")
            .persona(persona)
            .build();

    private final List<Cliente> listaClientes = new ArrayList<>();



    @Test
    void shouldObtenerClientes() {
        when(clienteRepository.encontrarClientes(any()))
                .thenReturn(listaClientes);
        List<Cliente> listaParcial = clienteRepository.encontrarClientes(any());
        Assertions.assertThat(listaParcial).isNotNull();

    }

    @Test
    void obtenerClientePorId() {
        when(clienteRepository.encontrarPorId(any())).thenReturn(cliente);
        Cliente clienteEncontrado = clienteRepository.encontrarPorId(1);
        Assertions.assertThat(clienteEncontrado).isInstanceOf(Cliente.class);
    }

    @Test
    void guardarCliente() {
        when(clienteRepository.guardarCliente(any())).thenReturn(cliente);
        Cliente clienteEncontrado = clienteRepository.guardarCliente(any());
        Assertions.assertThat(clienteEncontrado).isInstanceOf(Cliente.class);
    }

}

