package co.com.banco.jpa.movimiento;

import co.com.banco.jpa.cuenta.CuentaData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "T_MOVIMIENTOS")
public class MovimientoData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "fecha_movimiento")
    private Date fechaMovimiento;

    @NotEmpty
    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @NotEmpty
    @Column(name = "valor_movimiento")
    private Long valorMovimiento;

    @NotEmpty
    @Column(name = "saldo")
    private Long saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta", referencedColumnName = "id")
    private CuentaData cuentaData;

}
