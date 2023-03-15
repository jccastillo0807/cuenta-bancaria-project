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
    @Column(name = "tipo_documento")
    private String numeroDocumento;

    @NotEmpty
    @Column(name = "numero_documento", length = 20)
    private String tipoDocumento;

    @NotEmpty
    @Column(name = "primer_nombre", length = 50)
    private String primerNombre;

    @NotEmpty
    @Column(name = "segundo_nombre", length = 50)
    private String segundoNombre;

    @NotEmpty
    @Column(name = "primer_apellido", length = 50)
    private String primerApellido;

    @NotEmpty
    @Column(name = "segundo_apellido", length = 50)
    private String segundoApellido;

    @NotEmpty
    @Column(name = "genero")
    private String genero;

    @NotEmpty
    @Column(name = "direccion")
    private String direccion;

    @NotEmpty
    @Column(name = "telefoo")
    private String telefono;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personaData")
    private List<ClienteData> clienteDataList;
}
