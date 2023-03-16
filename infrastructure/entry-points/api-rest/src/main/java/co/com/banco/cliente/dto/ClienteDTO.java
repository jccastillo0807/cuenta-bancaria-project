package co.com.banco.cliente.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDTO {
    private String nombres;
    private String direccion;
    private String telefono;
    private String contrasena;
    private String estado;
}
