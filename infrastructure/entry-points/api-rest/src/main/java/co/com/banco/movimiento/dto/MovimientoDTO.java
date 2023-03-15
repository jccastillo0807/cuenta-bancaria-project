package co.com.banco.movimiento.dto;

import co.com.banco.model.cliente.Cliente;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class MovimientoDTO implements Serializable {
    private Integer id;
    private Date fecha;
    private String tipoMovimiento;
    private Long valorMovimiento;
    private Long saldo;
    private Cliente cliente;
    private static final long serialVersionUID = 1L;
}