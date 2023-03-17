package co.com.banco.usecase.cliente;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClienteUseCaseTest {

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    PersonaRepository personaRepository;

    @InjectMocks
    ClienteUseCase clienteUseCase;

    private Persona persona = Persona.builder()
            .id(1)
            .numeroDocumento("1234567")
            .tipoDocumento("Cedula")
            .nombre("Test_name")
            .apellido("apellido")
            .genero("genero")
            .direccion("direccion")
            .telefono("telefono")
            .build();

    private Cliente cliente = Cliente.builder()
            .id(1)
            .password("1234")
            .usuario("test")
            .estado("estado_test")
            .persona(persona)
            .build();

    private List<Cliente> listaClientes = new ArrayList<>();

    @BeforeAll
    public void setUp() {
        listaClientes.add(cliente);
    }

    @Test
    void shouldObtenerClientes() {
        when(clienteRepository.encontrarClientes())
                .thenReturn(listaClientes);
        Assertions.assertThat(listaClientes).isNotNull();

    }

    @Test
    void obtenerClientePorId() {
    }

    @Test
    void guardarCliente() {
    }

    @Test
    void inactivarCliente() {
    }

    @Test
    void actualizarCliente() {
    }
}

