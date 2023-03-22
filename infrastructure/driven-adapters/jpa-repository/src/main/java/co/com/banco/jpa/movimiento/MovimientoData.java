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

//    @NotEmpty
    @Column(name = "fecha_movimiento", nullable = false)
    private Date fechaMovimiento;

    @NotEmpty
    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

//    @NotEmpty
    @Column(name = "valor_movimiento", nullable = false)
    private Long valorMovimiento;

//    @NotEmpty
    @Column(name = "saldo", nullable = false)
    private Long saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id")
    private CuentaData cuentaData;

}
