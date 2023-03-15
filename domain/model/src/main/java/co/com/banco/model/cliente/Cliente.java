package co.com.banco.model.cliente;

import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Cliente {
    private Integer id;
    private String password;
    private String usuario;
    private Persona idPersona;
    private Cuenta idCuenta;
}
