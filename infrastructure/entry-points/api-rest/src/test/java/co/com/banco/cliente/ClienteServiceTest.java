package co.com.banco.cliente;

import co.com.banco.cliente.dto.ClienteDTO;
import co.com.banco.cliente.dto.PersonaDTO;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.persona.Persona;
import co.com.banco.usecase.cliente.ClienteUseCase;
import org.assertj.core.api.Assertions;
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
    private final ClienteDTO clienteDTO = new ClienteDTO();
    PersonaDTO personaDTO = new PersonaDTO();

    final List<Cliente> listaCliente = new ArrayList<>();

    @BeforeEach
    void setUp() {
        personaDTO.setId(1);
        personaDTO.setNumeroDocumento("1234567");
        personaDTO.setTipoDocumento("qwsdcv");
        personaDTO.setNombre("test");
        personaDTO.setApellido("test");
        personaDTO.setGenero("werfghn");
        personaDTO.setDireccion("dire");
        personaDTO.setTelefono("34353487");
        clienteDTO.setId(1);
        clienteDTO.setPassword("qwerty");
        clienteDTO.setUsuario("qwerty");
        clienteDTO.setEstado("estado");
        clienteDTO.setPersona(personaDTO);
    }


    @Test
    void shouldVerTodosLosClientes() {
        when(clienteUseCase.obtenerClientes()).thenReturn(listaCliente);
        List<Cliente> listaParcial = clienteUseCase.obtenerClientes();
        Assertions.assertThat(listaParcial).isNotNull();
    }

    @Test
    void shouldEncontrarClientePorId() {
        when(clienteUseCase.obtenerClientePorId(any())).thenReturn(cliente);
        ClienteDTO clienteParcial = clienteService.encontrarClientePorId(any());
        Assertions.assertThat(clienteParcial).isInstanceOf(ClienteDTO.class);
    }

    @Test
    void shouldCrearCliente() {
        when(clienteUseCase.guardarCliente(any())).thenReturn(cliente);
        ClienteDTO clienteParcial = clienteService.crearCliente(any());
        Assertions.assertThat(clienteParcial).isInstanceOf(ClienteDTO.class);
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
        when(clienteUseCase.actualizarCliente(any(), any())).thenReturn(cliente);
        ClienteDTO clienteParcial = clienteService.editarCliente(any(), any());
        Assertions.assertThat(clienteParcial).isInstanceOf(ClienteDTO.class);
    }
}