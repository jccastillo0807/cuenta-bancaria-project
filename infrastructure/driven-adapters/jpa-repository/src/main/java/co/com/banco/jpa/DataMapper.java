package co.com.banco.jpa;

import co.com.banco.jpa.cliente.ClienteData;
import co.com.banco.jpa.cuenta.CuentaData;
import co.com.banco.jpa.movimiento.MovimientoData;
import co.com.banco.jpa.persona.PersonaData;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.persona.Persona;

import java.util.ArrayList;
import java.util.List;

public class DataMapper {

    private DataMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Movimiento convertirMovimientoDataAMovimiento(MovimientoData movimientoData){
        return Movimiento.builder()
                .id(movimientoData.getId())
                .fecha(movimientoData.getFechaMovimiento())
                .tipoMovimiento(movimientoData.getTipoMovimiento())
                .valorMovimiento(movimientoData.getValorMovimiento())
                .saldo(movimientoData.getSaldo())
                .cuenta(convertirCuentaDataACuenta(movimientoData.getCuentaData()))
                .build();
    }

    public static Cuenta convertirCuentaDataACuenta(CuentaData cuentaData){
        return Cuenta.builder()
                .id(cuentaData.getId())
                .numeroCuenta(cuentaData.getNumeroCuenta())
                .tipoCuenta(cuentaData.getTipoCuenta())
                .saldoInicial(cuentaData.getSaldoInicial())
                .estado(cuentaData.getEstado())
                .cliente(convertirClienteDataACliente(cuentaData.getClienteData()))
                .build();
    }

    public static Cliente convertirClienteDataACliente(ClienteData clienteData) {
        return Cliente.builder()
                .id(clienteData.getId())
                .password(clienteData.getPassword())
                .usuario(clienteData.getUsuario())
                .password(clienteData.getPassword())
                .estado(clienteData.getEstado())
//                .cuentaDataList(clienteData.getCuentaDataList())
                .persona(convertirPersonaDataAPersona(clienteData.getPersonaData()))
                .build();
    }

    public static Persona convertirPersonaDataAPersona(PersonaData personaData) {
        return Persona.builder()
                .id(personaData.getId())
                .numeroDocumento(personaData.getNumeroDocumento())
                .tipoDocumento(personaData.getTipoDocumento())
                .nombre(personaData.getNombre())
                .apellido(personaData.getApellido())
                .genero(personaData.getGenero())
                .direccion(personaData.getDireccion())
                .telefono(personaData.getTelefono())
                .build();
    }

    public static PersonaData convertirPersonaAPersonaData(Persona persona) {
        return PersonaData.builder()
                .id(persona.getId())
                .numeroDocumento(persona.getNumeroDocumento())
                .tipoDocumento(persona.getTipoDocumento())
                .nombre(persona.getNombre())
                .apellido(persona.getApellido())
                .genero(persona.getGenero())
                .direccion(persona.getDireccion())
                .telefono(persona.getTelefono())
                .build();
    }
    public static List<Movimiento> converitirListaMovimientosDataAListaMovimiento(Iterable<MovimientoData> movimientoDataIterable) {
        List<Movimiento> listaMovimientos = new ArrayList<>();
        movimientoDataIterable.forEach(
                movimientoData -> listaMovimientos.add(
                        convertirMovimientoDataAMovimiento(movimientoData)
                )
        );
        return listaMovimientos;
    }

    public static List<Cliente> convertirlistClienteDataAListCliente(Iterable<ClienteData> clienteDataIterable) {
        List<Cliente> listaClientes = new ArrayList<>();
        clienteDataIterable.forEach(
                clienteData -> listaClientes.add(
                        convertirClienteDataACliente(clienteData)
                )
        );
        return listaClientes;
    }


    public static ClienteData convertirClienteAClienteData(Cliente cliente) {
       return ClienteData.builder()
               .id(cliente.getId())
               .estado(cliente.getEstado())
               .password(cliente.getPassword())
               .usuario(cliente.getUsuario())
               .password(cliente.getPassword())
//                .cuentaDataList(clienteData.getCuentaDataList())
               .personaData(convertirPersonaAPersonaData(cliente.getPersona()))
               .build();
    }
}
