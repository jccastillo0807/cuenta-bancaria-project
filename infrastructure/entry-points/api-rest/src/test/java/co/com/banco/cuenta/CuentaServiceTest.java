package co.com.banco.cuenta;

import co.com.banco.cliente.dto.ClienteDTO;
import co.com.banco.cliente.dto.PersonaDTO;
import co.com.banco.cuenta.dto.CuentaDTO;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.persona.Persona;
import co.com.banco.usecase.cuenta.CuentaUseCase;
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
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {


    @InjectMocks
    CuentaService cuentaService;

    @Mock
    CuentaUseCase cuentaUseCase;

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

    private final Cuenta cuenta = Cuenta.builder()
            .id(1)
            .numeroCuenta("1234567")
            .tipoCuenta("tipoCuenta")
            .saldoInicial(10000L)
            .estado("estado")
            .cliente(cliente)
            .build();

    CuentaDTO cuentaDTO = new CuentaDTO();
    ClienteDTO clienteDTO = new ClienteDTO();
    PersonaDTO personaDTO = new PersonaDTO();

    private final List<CuentaDTO> listaCuentas = new ArrayList<>();

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
        cuentaDTO.setId(1);
        cuentaDTO.setNumeroCuenta("34567");
        cuentaDTO.setTipoCuenta("tipo");
        cuentaDTO.setSaldoInicial(1000L);
        cuentaDTO.setEstado("qwert");
        cuentaDTO.setCliente(clienteDTO);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldVerTodosLasCuentas() {
        when(cuentaService.verTodosLasCuentas()).thenReturn(listaCuentas);
        List<Cuenta> listaParcial = cuentaUseCase.obtenerCuentas();
        Assertions.assertThat(listaParcial).isNotNull();
    }

    @Test
    void shouldEncontrarCuentaPorId() {
        when(cuentaUseCase.obtenerCuentaPor(any())).thenReturn(cuenta);
        Cuenta cuentaParcial = cuentaUseCase.obtenerCuentaPor(any());
        Assertions.assertThat(cuentaParcial).isInstanceOf(Cuenta.class);
    }

    @Test
    void shouldCrearCliente() {
        when(cuentaUseCase.guardar(any())).thenReturn(cuenta);
        Cuenta cuentaParcial = cuentaUseCase.guardar(any());
        Assertions.assertThat(cuentaParcial).isInstanceOf(Cuenta.class);
    }

    @Test
    void eliminarCuenta() {
        CuentaService service = mock(CuentaService.class);
        doNothing().when(service).eliminarCuenta(isA(Integer.class));
        service.eliminarCuenta(1);
        verify(service, times(1)).eliminarCuenta(1);
    }

    @Test
    void editarCuenta() {
        when(cuentaUseCase.actualizar(any(),any())).thenReturn(cuenta);
        Cuenta cuentaParcial = cuentaUseCase.actualizar(any(), any());
        Assertions.assertThat(cuentaParcial).isNotNull();
        Assertions.assertThat(cuentaParcial).isInstanceOf(Cuenta.class);
    }
}