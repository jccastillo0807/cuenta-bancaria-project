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

    public static Movimiento convertirMovimientoDataAMovimiento(MovimientoData movimientoData) {
        return Movimiento.builder()
                .id(movimientoData.getId())
                .fechaMovimiento(movimientoData.getFechaMovimiento())
                .tipoMovimiento(movimientoData.getTipoMovimiento())
                .valorMovimiento(movimientoData.getValorMovimiento())
                .saldo(movimientoData.getSaldo())
                .saldoAnterior(movimientoData.getSaldoAnterior())
                .cuenta(convertirCuentaDataACuenta(movimientoData.getCuentaData()))
                .build();
    }

    public static MovimientoData convertirMovimientoAMovimientoData(Movimiento movimiento) {
        return MovimientoData.builder()
                .id(movimiento.getId())
                .fechaMovimiento(movimiento.getFechaMovimiento())
                .tipoMovimiento(movimiento.getTipoMovimiento())
                .valorMovimiento(movimiento.getValorMovimiento())
                .saldo(movimiento.getSaldo())
                .saldoAnterior(movimiento.getSaldoAnterior())
                .cuentaData(convertirCuentaACuentaData(movimiento.getCuenta()))
                .build();
    }

    public static Cuenta convertirCuentaDataACuenta(CuentaData cuentaData) {
        return Cuenta.builder()
                .id(cuentaData.getId())
                .numeroCuenta(cuentaData.getNumeroCuenta())
                .tipoCuenta(cuentaData.getTipoCuenta())
                .saldoInicial(cuentaData.getSaldoInicial())
                .estado(cuentaData.getEstado())
                .cliente(convertirClienteDataACliente(cuentaData.getClienteData()))
                .build();
    }

    public static CuentaData convertirCuentaACuentaData(Cuenta cuenta) {
        return CuentaData.builder()
                .id(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .clienteData(convertirClienteAClienteData(cuenta.getCliente()))
                .build();
    }

    public static Cliente convertirClienteDataACliente(ClienteData clienteData) {
        return Cliente.builder()
                .id(clienteData.getId())
                .password(clienteData.getPassword())
                .usuario(clienteData.getUsuario())
                .password(clienteData.getPassword())
                .estado(clienteData.getEstado())
                .persona(convertirPersonaDataAPersona(clienteData.getPersonaData()))
                .build();
    }

    public static ClienteData convertirClienteAClienteData(Cliente cliente) {
        return ClienteData.builder()
                .id(cliente.getId())
                .estado(cliente.getEstado())
                .password(cliente.getPassword())
                .usuario(cliente.getUsuario())
                .password(cliente.getPassword())
                .personaData(convertirPersonaAPersonaData(cliente.getPersona()))
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


    public static List<Cuenta> converitirListaCuentasDataAListaCuentas(Iterable<CuentaData> cuentasDataIterable) {
        List<Cuenta> listaCuentas = new ArrayList<>();
        cuentasDataIterable.forEach(
                cuentaData -> listaCuentas.add(
                        convertirCuentaDataACuenta(cuentaData)
                )
        );
        return listaCuentas;
    }


}
