package co.com.banco.reporte.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class ReporteDTO implements Serializable {
    private Integer idMovimiento;
    private Date fechaMovimiento;
    private String cliente;
    private String tipo;
    private Long saldoInicial;
    private String estado;
    private Long saldoDisponible;


    private static final long serialVersionUID = 1L;
}
