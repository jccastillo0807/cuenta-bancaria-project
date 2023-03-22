package co.com.banco.usecase.cuenta;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.cliente.gateways.ClienteRepository;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import co.com.banco.model.persona.Persona;
import co.com.banco.model.persona.gateways.PersonaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static co.com.banco.model.common.ValidationUtils.validarCamposCuenta;
import static co.com.banco.model.common.ValidationUtils.validarIdNulo;

@RequiredArgsConstructor
public class CuentaUseCase {

    public static final String INACTIVO = "INACTIVO";
    public static final String ACTIVO = "ACTIVO";
    private final CuentaRepository cuentaRepository;
    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;


    public List<Cuenta> obtenerCuentas() {
        return cuentaRepository.encontrarCuentas(ACTIVO);
    }

    public Cuenta obtenerCuentaPorId(Integer id) {
        validarIdNulo(id);
        Cuenta cuentaEncontrada = cuentaRepository.encontrarCuentaPorId(id);
        if (Objects.isNull(cuentaEncontrada)) {
            throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
        }
        return cuentaEncontrada;
    }


    public Cuenta guardarCuenta(Cuenta cuenta) {
        validarCamposCuenta(cuenta);
        validarSiExisteClienteYPersona(cuenta);
        Cuenta cuentaEncontrada = cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
        if (Objects.isNull(cuentaEncontrada)) {
            return cuentaRepository.guardarCuenta(cuenta);
        }
        throw new BusinessException(BusinessException.Type.CUENTA_YA_EXISTE);
    }

    public void eliminarCuenta(Integer id) {
        Cuenta cuentaEncontrada = cuentaRepository.encontrarCuentaPorId(id);
        if (Objects.isNull(cuentaEncontrada)) {
            throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
        }
        cuentaEncontrada.setEstado(INACTIVO);
        cuentaRepository.guardarCuenta(cuentaEncontrada);
    }

    public Cuenta actualizarCuenta(Integer id, Cuenta cuenta) {
        validarIdNulo(id);
        validarCamposCuenta(cuenta);
        Cuenta cuentaEnBaseDeDatos = cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta());//cuentaRepository.encontrarCuentaPorId(id);
        if (Objects.isNull(cuentaEnBaseDeDatos)) {
            throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
        }

        cuentaEnBaseDeDatos.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaEnBaseDeDatos.setEstado(cuenta.getEstado());
        cuentaEnBaseDeDatos.setSaldoInicial(cuenta.getSaldoInicial());
        return cuentaRepository.guardarCuenta(cuentaEnBaseDeDatos);
    }

    private void validarSiExisteClienteYPersona(Cuenta cuenta) {
        Persona existePersona = personaRepository.encontrarPersonaPorId(cuenta.getCliente().getPersona().getId());
        Cliente existeCliente = clienteRepository.encontrarPorId(cuenta.getCliente().getId());

        if (Objects.isNull(existePersona)) {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
        if (Objects.isNull(existeCliente)) {
            throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
        }
    }


}
