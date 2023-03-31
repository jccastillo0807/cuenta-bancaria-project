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

    public Cuenta obtenerCuentaPor(Integer id) {
        validarIdNulo(id);
        Cuenta cuentaEncontrada = cuentaRepository.encontrarCuentaPorId(id);
        if (Objects.isNull(cuentaEncontrada) || !cuentaEncontrada.getEstado().equalsIgnoreCase(ACTIVO)) {
            throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
        }
        return cuentaEncontrada;
    }


    public Cuenta guardar(Cuenta cuenta) {
        validarCamposCuenta(cuenta);
        validarTipoCuenta(cuenta.getTipoCuenta());
        validarSiExisteClienteYPersona(cuenta);
        validarSiExisteCuenta(cuenta);
        return cuentaRepository.guardarCuenta(cuenta);
    }


    public void eliminar(Integer id) {
        Cuenta cuentaEncontrada = obtenerCuentaPor(id);
        cuentaEncontrada.setEstado(INACTIVO);
        cuentaRepository.guardarCuenta(cuentaEncontrada);
    }

    public Cuenta actualizar(Integer id, Cuenta cuenta) {
        validarIdNulo(id);
        validarCamposCuenta(cuenta);
        validarTipoCuenta(cuenta.getTipoCuenta());
        Cuenta cuentaEnBaseDeDatos = cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
        if (Objects.isNull(cuentaEnBaseDeDatos) || !cuentaEnBaseDeDatos.getEstado().equalsIgnoreCase(ACTIVO)) {
            throw new BusinessException(BusinessException.Type.CUENTA_NO_ENCONTRADA);
        }
        cuentaEnBaseDeDatos.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaEnBaseDeDatos.setEstado(cuenta.getEstado());
        cuentaEnBaseDeDatos.setSaldoInicial(cuenta.getSaldoInicial());
        return cuentaRepository.guardarCuenta(cuentaEnBaseDeDatos);
    }

    private void validarTipoCuenta(String tipoCuenta) {
        if (!tipoCuenta.equalsIgnoreCase("AHORRO") && !tipoCuenta.equalsIgnoreCase("CORRIENTE")) {
            throw new BusinessException(BusinessException.Type.TIPO_CUENTA_NO_VALIDO);
        }
    }

    private void validarSiExisteCuenta(Cuenta cuenta) {
        Cuenta cuentaEncontrada = cuentaRepository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
        if (Objects.nonNull(cuentaEncontrada)) {
            throw new BusinessException(BusinessException.Type.CUENTA_YA_EXISTE);
        }
    }

    private void validarSiExisteClienteYPersona(Cuenta cuenta) {
        Persona existePersona = personaRepository.encontrarPorTipoYNumeroDocumento(
                cuenta.getCliente().getPersona().getTipoDocumento(),
                cuenta.getCliente().getPersona().getNumeroDocumento()
        );
        Cliente existeCliente = clienteRepository.encontrarPorId(cuenta.getCliente().getId());

        if (Objects.isNull(existePersona)) {
            throw new BusinessException(BusinessException.Type.ERROR_PERSONA_NO_REGISTRADA);
        }
        if (Objects.isNull(existeCliente)) {
            throw new BusinessException(BusinessException.Type.ERROR_CLIENTE_NO_REGISTRADO);
        }
    }


}
