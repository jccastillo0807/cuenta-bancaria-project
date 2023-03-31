package co.com.banco.jpa.cuenta;

import co.com.banco.jpa.cliente.ClienteData;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@Setter
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
    @Size(min = 6, max = 6, message = "El número de cuenta debe tener 6 digitos.")
    @Column(name = "numero_cuenta", unique = true)
    private String numeroCuenta;

    @NotEmpty
    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    @PositiveOrZero
    @Column(name = "saldo_actual", nullable = false)
    private Long saldoInicial;

    @NotEmpty
    @Column(name = "estado_cuenta")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private ClienteData clienteData;
}

