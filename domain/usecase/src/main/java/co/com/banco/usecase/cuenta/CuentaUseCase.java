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

import static co.com.banco.model.common.ValidationUtils.validarCuenta;

@RequiredArgsConstructor
public class CuentaUseCase {

    public static final String INACTIVO = "INACTIVO";
    private final CuentaRepository cuentaRepository;
    private final PersonaRepository personaRepository;
    private final ClienteRepository clienteRepository;


    public List<Cuenta> obtenerCuentas() {
        return cuentaRepository.encontrarCuentas();
    }

    public Cuenta obtenerCuentaPorId(Integer id) {
        if (id != null) {
            Cuenta cuentaEncontrada = cuentaRepository.encontrarCuentaPorId(id);
            if (!Objects.isNull(cuentaEncontrada)) {
                return cuentaEncontrada;
            } else {
                throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
            }
        }
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    public Cuenta guardarCuenta(Cuenta cuenta) {
        Boolean validacionCamposCuenta = validarCuenta(cuenta);
        Boolean validarEntidadClienteYPersona = validarSiExisteClienteYPersona(cuenta);
        if (validacionCamposCuenta &&validarEntidadClienteYPersona) {
                return cuentaRepository.guardarCuenta(cuenta);
        }
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    public void eliminarCuenta(Integer id) {
        if (id != null) {
            Cuenta cuentaEncontrada = cuentaRepository.encontrarCuentaPorId(id);
            if (!Objects.isNull(cuentaEncontrada)) {
                cuentaEncontrada.setEstado(INACTIVO);
                cuentaRepository.guardarCuenta(cuentaEncontrada);
            } else {
                throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
            }
        }
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    public Cuenta actualizarCuenta(Integer id, Cuenta cuenta) {
        if (id != null) {
            Cuenta cuentaEnBaseDeDatos = cuentaRepository.encontrarCuentaPorId(id);
            if (!Objects.isNull(cuentaEnBaseDeDatos)) {
                cuentaEnBaseDeDatos.setTipoCuenta(cuenta.getTipoCuenta());
                cuentaEnBaseDeDatos.setEstado(cuenta.getEstado());
                cuentaEnBaseDeDatos.setSaldoInicial(cuenta.getSaldoInicial());
                return cuentaRepository.guardarCuenta(cuentaEnBaseDeDatos);
            } else {
                throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
            }
        }
        throw new BusinessException(BusinessException.Type.ERROR_BASE_DATOS);
    }

    private Boolean validarSiExisteClienteYPersona(Cuenta cuenta){
        Persona existePersona = personaRepository.findById(cuenta.getCliente().getPersona().getId());
        Cliente existeCliente = clienteRepository.encontrarPorId(cuenta.getCliente().getId());
        if (Objects.nonNull(existePersona)){
            if ( Objects.nonNull(existeCliente)) {
                return true;
            }else {
                throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
            }
        } else {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
    }
    
}
