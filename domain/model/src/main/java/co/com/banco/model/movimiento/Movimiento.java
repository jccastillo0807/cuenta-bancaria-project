package co.com.banco.model.movimiento;

import co.com.banco.model.cliente.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Movimiento {
    private Integer id;
    private Date fecha;
    private String tipoMovimiento;
    private Long valorMovimiento;
    private Long saldo;
    private Cliente cliente;
}
