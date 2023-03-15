package co.com.banco.model.cuenta;


import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.movimiento.Movimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cuenta {
    private Integer id;
    private String numeroCuenta;
    private String tipoCuenta;
    private Long saldoInicial;
    private String estado;
    private List<Movimiento> listaMovimientos;
    private Cliente cliente;
}
