package co.com.banco.movimiento;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.persona.Persona;
import co.com.banco.usecase.movimiento.MovimientoUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovimientoServiceTest {

    @InjectMocks
    MovimientoService movimientoService;

    @Mock
    MovimientoUseCase movimientoUseCase;

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

    private final Movimiento movimiento = Movimiento.builder()
            .id(1)
            .fechaMovimiento(new Date())
            .tipoMovimiento("fechaMovimiento")
            .valorMovimiento(11000L)
            .saldo(100L)
            .cuenta(cuenta)
            .build();

    private final List<Movimiento> listaMovimientos = new ArrayList<>();

    @Test
    void shouldVerTodosLosMovimientos() {
        when(movimientoUseCase.obtenerMovimientos()).thenReturn(listaMovimientos);
        List<Movimiento> listaParcial = movimientoUseCase.obtenerMovimientos();
        Assertions.assertThat(listaParcial).isNotNull();
    }

    @Test
    void shouldEncontrarPorId() {
        when(movimientoUseCase.encontrarPorId(any())).thenReturn(movimiento);
        Movimiento movimientoParcial = movimientoUseCase.encontrarPorId(any());
        Assertions.assertThat(movimientoParcial).isInstanceOf(Movimiento.class);
    }

    @Test
    void shouldCrearMovimiento() {
        when(movimientoUseCase.guardarMovimiento(any())).thenReturn(movimiento);
        Movimiento movimientoParcial = movimientoUseCase.guardarMovimiento(any());
        Assertions.assertThat(movimientoParcial).isInstanceOf(Movimiento.class);
    }
}