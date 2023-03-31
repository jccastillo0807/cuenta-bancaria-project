package co.com.banco.jpa.cliente;

import co.com.banco.jpa.cuenta.CuentaData;
import co.com.banco.jpa.persona.PersonaData;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "T_CLIENTES")
public class ClienteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 4, max = 4, message = "El password debe tener 4 números.")
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Size(min = 5, max = 25)
    @Column(name = "usuario", unique = true)
    private String usuario;

    @NotEmpty
    @Size(min = 5, max = 15)
    @Column(name = "estado")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private PersonaData personaData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteData")
    private List<CuentaData> cuentaDataList;
}
