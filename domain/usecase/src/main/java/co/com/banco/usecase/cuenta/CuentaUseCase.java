package co.com.banco.usecase.cuenta;

import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import co.com.banco.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CuentaUseCase {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    public List<Cuenta> obtenerCuentas() {
        return cuentaRepository.encontrarCuentas();
    }

    public Cuenta obtenerCuentaPorId(Integer id) {
        return cuentaRepository.encontrarCuentaPorId(id);
    }

    public Cuenta guardarCuenta(Cuenta cuenta) {
        return cuentaRepository.guardarCuenta(cuenta);
    }

    public void eliminarCuenta(Integer id) {
        Cuenta cuentaEnBaseDatos = cuentaRepository.encontrarCuentaPorId(id);
        cuentaEnBaseDatos.setEstado("INACTIVO");
        cuentaRepository.guardarCuenta(cuentaEnBaseDatos);
    }

    public Cuenta actualizarCuenta(Integer id, Cuenta cuenta) {
        Cuenta cuentaEnBaseDeDatos = cuentaRepository.encontrarCuentaPorId(id);
    cuentaEnBaseDeDatos.setTipoCuenta(cuenta.getTipoCuenta());
    cuentaEnBaseDeDatos.setEstado(cuenta.getEstado());
    cuentaEnBaseDeDatos.setSaldoInicial(cuenta.getSaldoInicial());
    return cuentaRepository.guardarCuenta(cuentaEnBaseDeDatos);
    }
}
