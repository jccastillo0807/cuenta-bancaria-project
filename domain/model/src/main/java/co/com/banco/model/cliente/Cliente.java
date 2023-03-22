package co.com.banco.model.cliente;

import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.persona.Persona;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cliente {
    private Integer id;
    private String password;
    private String usuario;
    private String estado;
    private Persona persona;
    private List<Cuenta> cuentaDataList;
}
