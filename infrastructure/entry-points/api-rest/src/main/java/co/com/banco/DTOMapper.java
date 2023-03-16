package co.com.banco;

import co.com.banco.cliente.dto.ClienteDTO;
import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.movimiento.dto.MovimientoDTO;
import co.com.banco.reporte.dto.ReporteDTO;

import java.util.ArrayList;
import java.util.List;

public class DTOMapper {
    private DTOMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ClienteDTO convertirClienteaClienteDTO(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombres(cliente.getPersona().getNombre()+" "+ cliente.getPersona().getApellido());
        clienteDTO.setDireccion(cliente.getPersona().getDireccion());
        clienteDTO.setTelefono(cliente.getPersona().getTelefono());
        clienteDTO.setContrasena(cliente.getPassword());
        clienteDTO.setEstado(cliente.getEstado());

        return clienteDTO;
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

    public static MovimientoDTO movimientoAMovimientoDTO(Movimiento movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(movimiento.getId());
        movimientoDTO.setFecha(movimiento.getFechaMovimiento());
        movimientoDTO.setTipoMovimiento(movimiento.getTipoMovimiento());
        movimientoDTO.setValorMovimiento(movimiento.getValorMovimiento());
        movimientoDTO.setSaldo(movimiento.getSaldo());

        return movimientoDTO;
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

    public static List<ReporteDTO> listMovimientoAListReporteDTO(List<Movimiento> listaMovimientos){
        List<ReporteDTO> listaReporteDTO = new ArrayList<>();
        listaMovimientos.forEach(
                movimiento -> listaReporteDTO.add(
                        movimientoAReporteDTO(movimiento)
                )
        );
        return listaReporteDTO;
    }


    public static List<ClienteDTO> listClienteAListClienteDTO(List<Cliente> listaCliente){
        List<ClienteDTO> listClienteDTO = new ArrayList<>();
        listaCliente.forEach(
                cliente -> listClienteDTO.add(
                        convertirClienteaClienteDTO(cliente)
                )
        );

        return listClienteDTO;
    }


}
