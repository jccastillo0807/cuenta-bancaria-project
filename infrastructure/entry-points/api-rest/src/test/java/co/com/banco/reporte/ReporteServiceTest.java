package co.com.banco.reporte;

import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.reporte.dto.ReporteDTO;
import co.com.banco.usecase.reporte.ReporteUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
class ReporteServiceTest {

    @InjectMocks
    ReporteService reporteService;

    @Mock
    ReporteUseCase reporteUseCase;

    static ReporteDTO reporteDTO = new ReporteDTO();

    static List<ReporteDTO> listaReporteDTO = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        reporteDTO.setIdMovimiento(1);
        reporteDTO.setFechaMovimiento(new Date());
        reporteDTO.setCliente("nombre apellido");
        reporteDTO.setTipo("tipo");
        reporteDTO.setSaldoInicial(10000L);
        reporteDTO.setEstado("estado");
        reporteDTO.setSaldoDisponible(15555L);
        listaReporteDTO.add(reporteDTO);
    }


    @Test
    void shouldGenerarReporte() {
        when(reporteService.generarReporte(any(), any())).thenReturn(listaReporteDTO);
        List<Movimiento> listaParcial =
                reporteUseCase.generarReporteEntreFechas(any(), any());
        Assertions.assertThat(listaParcial).isNotNull();
    }
}