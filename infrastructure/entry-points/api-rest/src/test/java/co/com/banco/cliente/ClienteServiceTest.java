package co.com.banco.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.persona.Persona;
import co.com.banco.usecase.cliente.ClienteUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteUseCase clienteUseCase;

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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldVerTodosLosClientes() {
        when(clienteService.verTodosLosClientes()).thenReturn(listaClientes);
        List<Cliente> listaParcial = clienteUseCase.obtenerClientes();
        Assertions.assertThat(listaParcial).isNotNull();
    }

    @Test
    void shouldEncontrarClientePorId() {
        when(clienteService.encontrarClientePorId(any())).thenReturn(cliente);
        Cliente clienteParcial = clienteService.encontrarClientePorId(any());
        Assertions.assertThat(clienteParcial).isInstanceOf(Cliente.class);
    }

    @Test
    void shouldCrearCliente() {
        when(clienteService.crearCliente(any())).thenReturn(cliente);
        Cliente clienteParcial = clienteService.crearCliente(any());
        Assertions.assertThat(clienteParcial).isInstanceOf(Cliente.class);
    }

    @Test
    void shouldEliminarCliente() {
        ClienteService service = mock(ClienteService.class);
        doNothing().when(service).eliminarCliente(isA(Integer.class));
        service.eliminarCliente(1);
        verify(service, times(1)).eliminarCliente(1);
    }

    @Test
    void shouldEditarCliente() {
        when(clienteService.editarCliente(any(),any())).thenReturn(cliente);
        Cliente clienteParcial = clienteService.editarCliente(any(), any());
        Assertions.assertThat(clienteParcial).isInstanceOf(Cliente.class);
    }
}