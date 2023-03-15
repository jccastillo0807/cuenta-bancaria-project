package co.com.banco.jpa.cuenta;

import co.com.banco.jpa.cliente.ClienteData;
import co.com.banco.jpa.movimiento.MovimientoData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "T_CUENTAS")
public class CuentaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @NotEmpty
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    @NotEmpty
    @Column(name = "saldo_actual")
    private Long saldoInicial;

    @NotEmpty
    @Column(name = "estado_cuenta")
    private String estado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cuentaData")
    private List<MovimientoData> movimientoDataList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private ClienteData clienteData;
}

