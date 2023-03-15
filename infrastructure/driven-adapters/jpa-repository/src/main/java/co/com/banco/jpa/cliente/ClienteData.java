package co.com.banco.jpa.cliente;

import co.com.banco.jpa.cuenta.CuentaData;
import co.com.banco.jpa.persona.PersonaData;
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
@Table(name = "T_CLIENTES")
public class ClienteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Column(name = "usuario")
    private String usuario;

    @NotEmpty
    @Column(name = "estado")
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private PersonaData personaData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clienteData")
    private List<CuentaData> cuentaDataList;
}
