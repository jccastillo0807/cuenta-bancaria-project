package co.com.banco;

import co.com.banco.cliente.dto.ClienteDTO;
import co.com.banco.cliente.dto.ClienteReporteDTO;
import co.com.banco.cliente.dto.PersonaDTO;
import co.com.banco.cuenta.dto.CuentaDTO;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.persona.Persona;
import co.com.banco.movimiento.dto.MovimientoDTO;
import co.com.banco.reporte.dto.ReporteDTO;

import java.util.ArrayList;
import java.util.List;

public class DTOMapper {
    private DTOMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static PersonaDTO convertirPersonaaPersonaDTO(Persona persona) {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setId(persona.getId());
        personaDTO.setNumeroDocumento(persona.getNumeroDocumento());
        personaDTO.setTipoDocumento(persona.getTipoDocumento());
        personaDTO.setNombre(persona.getNombre());
        personaDTO.setApellido(persona.getApellido());
        personaDTO.setDireccion(persona.getDireccion());
        personaDTO.setTelefono(persona.getTelefono());
        personaDTO.setGenero(persona.getGenero());
        return personaDTO;
    }

    public static ClienteDTO convertirClienteAClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setPassword(cliente.getPassword());
        clienteDTO.setUsuario(cliente.getUsuario());
        clienteDTO.setEstado(cliente.getEstado());
        clienteDTO.setPersona(convertirPersonaaPersonaDTO(cliente.getPersona()));

        return clienteDTO;
    }

    public static ClienteReporteDTO convertirClienteaClienteReporteDTO(Cliente cliente) {
        ClienteReporteDTO clienteReporteDTO = new ClienteReporteDTO();
        clienteReporteDTO.setNombres(cliente.getPersona().getNombre() + " " + cliente.getPersona().getApellido());
        clienteReporteDTO.setDireccion(cliente.getPersona().getDireccion());
        clienteReporteDTO.setTelefono(cliente.getPersona().getTelefono());
        clienteReporteDTO.setContrasena(cliente.getPassword());
        clienteReporteDTO.setEstado(cliente.getEstado());
        return clienteReporteDTO;
    }

    public static CuentaDTO convertirCuentaACuentaDTO(Cuenta cuenta) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(cuenta.getId());
        cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaDTO.setEstado(cuenta.getEstado());
        cuentaDTO.setCliente(convertirClienteAClienteDTO(cuenta.getCliente()));
        return cuentaDTO;
    }

    public static MovimientoDTO movimientoAMovimientoDTO(Movimiento movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(movimiento.getId());
        movimientoDTO.setFecha(movimiento.getFechaMovimiento());
        movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoDTO.setValorMovimiento(movimiento.getValorMovimiento());
        movimientoDTO.setSaldo(movimiento.getSaldo());
        movimientoDTO.setCuenta(convertirCuentaACuentaDTO(movimiento.getCuenta()));

        return movimientoDTO;
    }

    private static ReporteDTO movimientoAReporteDTO(Movimiento movimiento) {
        ReporteDTO reporteDTO = new ReporteDTO();
        reporteDTO.setIdMovimiento(movimiento.getId());
        reporteDTO.setFechaMovimiento(movimiento.getFechaMovimiento());
        reporteDTO.setCliente(movimiento.getCuenta().getCliente().getPersona().getNombre() + " " +
                movimiento.getCuenta().getCliente().getPersona().getApellido());
        reporteDTO.setTipo(movimiento.getCuenta().getTipoCuenta());
        reporteDTO.setSaldoInicial(movimiento.getSaldo());
        reporteDTO.setEstado(movimiento.getCuenta().getEstado());
        reporteDTO.setSaldoDisponible(movimiento.getCuenta().getSaldoInicial());

        return reporteDTO;
    }

    public static List<ClienteDTO> listClienteAListaClienteDTO(List<Cliente> listaCliente) {
        List<ClienteDTO> listaClientesDTO = new ArrayList<>();
        listaCliente.forEach(
                cliente -> listaClientesDTO.add(
                        convertirClienteAClienteDTO(cliente)
                )
        );
        return listaClientesDTO;
    }

    public static List<CuentaDTO> listCuentaAListaCuentaDTO(List<Cuenta> listaCuentas) {
        List<CuentaDTO> listaCuentaDTOS = new ArrayList<>();
        listaCuentas.forEach(
                cuenta -> listaCuentaDTOS.add(
                        convertirCuentaACuentaDTO(cuenta)
                )
        );

        return listaCuentaDTOS;
    }


    public static List<MovimientoDTO> listMovimientosAListMovimientosDTO(List<Movimiento> listaMovimientos) {
        List<MovimientoDTO> listaMovimientosDTO = new ArrayList<>();
        listaMovimientos.forEach(
                movimiento -> listaMovimientosDTO.add(
                        movimientoAMovimientoDTO(movimiento)
                )
        );

        return listaMovimientosDTO;
    }

    public static List<ReporteDTO> listMovimientoAListReporteDTO(List<Movimiento> listaMovimientos) {
        List<ReporteDTO> listaReporteDTO = new ArrayList<>();
        listaMovimientos.forEach(
                movimiento -> listaReporteDTO.add(
                        movimientoAReporteDTO(movimiento)
                )
        );
        return listaReporteDTO;
    }


    public static List<ClienteReporteDTO> listClienteAListClienteReporteDTO(List<Cliente> listaCliente) {
        List<ClienteReporteDTO> listClienteReporteDTO = new ArrayList<>();
        listaCliente.forEach(
                cliente -> listClienteReporteDTO.add(
                        convertirClienteaClienteReporteDTO(cliente)
                )
        );

        return listClienteReporteDTO;
    }


}
