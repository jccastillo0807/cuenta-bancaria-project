package co.com.banco.jpa.persona;

import co.com.banco.jpa.cliente.ClienteData;
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
@Table(name = "T_PERSONAS")
public class PersonaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;

    @NotEmpty
    @Column(name = "tipo_documento", length = 20)
    private String tipoDocumento;

    @NotEmpty
    @Column(name = "nombre", length = 50)
    private String nombre;

    @NotEmpty
    @Column(name = "apellido", length = 50)
    private String apellido;

    @NotEmpty
    @Column(name = "genero")
    private String genero;

    @NotEmpty
    @Column(name = "direccion")
    private String direccion;

    @NotEmpty
    @Column(name = "telefono")
    private String telefono;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personaData")
    private List<ClienteData> clienteDataList;
}
