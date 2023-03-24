package co.com.banco.jpa.movimiento;

import co.com.banco.jpa.cuenta.CuentaData;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "T_MOVIMIENTOS")
public class MovimientoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fecha_movimiento", nullable = false)
    private Date fechaMovimiento;

    @NotEmpty
    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @Column(name = "valor_movimiento", nullable = false)
    private Long valorMovimiento;

    @Column(name = "saldo", nullable = false)
    private Long saldo;

    @Column(name = "saldo_anterior", nullable = false)
    private Long saldoAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id")
    private CuentaData cuentaData;

}
