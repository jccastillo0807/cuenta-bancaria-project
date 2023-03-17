package co.com.banco.cuenta;

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

    private final List<Cuenta> listaCuentas = new ArrayList<>();

    @BeforeEach
    void setUp() {
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
        when(cuentaService.encontrarCuentaPorId(any())).thenReturn(cuenta);
        Cuenta cuentaParcial = cuentaUseCase.obtenerCuentaPorId(any());
        Assertions.assertThat(cuentaParcial).isInstanceOf(Cuenta.class);
    }

    @Test
    void shouldCrearCliente() {
        when(cuentaService.crearCuenta(any())).thenReturn(cuenta);
        Cuenta cuentaParcial = cuentaUseCase.guardarCuenta(any());
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
        when(cuentaService.editarCuenta(any(),any())).thenReturn(cuenta);
        Cuenta cuentaParcial = cuentaUseCase.actualizarCuenta(any(), any());
        Assertions.assertThat(cuentaParcial).isNotNull();
        Assertions.assertThat(cuentaParcial).isInstanceOf(Cuenta.class);
    }
}