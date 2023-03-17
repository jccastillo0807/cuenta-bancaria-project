package co.com.banco.model.common;

import co.com.banco.model.cliente.Cliente;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.movimiento.Movimiento;
import co.com.banco.model.persona.Persona;

import static co.com.banco.model.common.StringUtils.isEmpty;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void verify(BusinessException.Type error) {
        if (nonNull(error)) {
            throw new BusinessException(error);
        }
    }

    public static BusinessException.Type required(BusinessException.Type[] exceptions, Object[] data) {
        BusinessException.Type required = null;
        for (int i = 0; i < data.length && isNull(required); i++) {
            boolean isRequired = (data[i] instanceof String && isEmpty((String) data[i])) || isNull(data[i]);

            if (isRequired) {
                required = exceptions[i];
            }
        }
        return required;
    }

    public static Boolean validarPersona(Persona persona){
        if (StringUtils.isEmpty(persona.getNumeroDocumento())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }
        if (StringUtils.isEmpty(persona.getTipoDocumento())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }
        if (StringUtils.isEmpty(persona.getNombre())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }
        if (StringUtils.isEmpty(persona.getApellido())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }
        if (StringUtils.isEmpty(persona.getDireccion())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }
        if (StringUtils.isEmpty(persona.getTelefono())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_PERSONA);
        }
        return true;
    }

    public static Boolean validarCliente(Cliente cliente){
        if (StringUtils.isEmpty(cliente.getPassword())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CLIENTE);
        }
        if (StringUtils.isEmpty(cliente.getUsuario())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CLIENTE);
        }
        if (StringUtils.isEmpty(cliente.getEstado())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CLIENTE);
        }
        if (Boolean.FALSE.equals(validarPersona(cliente.getPersona()))) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CLIENTE);
        }
        return true;
    }

    public static Boolean validarCuenta(Cuenta cuenta){
        if (StringUtils.isEmpty(cuenta.getNumeroCuenta())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CUENTA);
        }
        if (StringUtils.isEmpty(cuenta.getTipoCuenta())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CUENTA);
        }
        if (cuenta.getSaldoInicial() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CUENTA);
        }
        if (StringUtils.isEmpty(cuenta.getEstado())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CUENTA);
        }
        if (Boolean.FALSE.equals(validarCliente(cuenta.getCliente()))) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_CUENTA);
        }

        return true;
    }

    public static Boolean validarMovimiento(Movimiento movimiento){
        if (movimiento.getFechaMovimiento() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_MOVIMIENTO);
        }
        if (movimiento.getValorMovimiento() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_MOVIMIENTO);
        }
        if (movimiento.getTipoMovimiento() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_MOVIMIENTO);
        }
        if (movimiento.getSaldo() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_MOVIMIENTO);
        }
        if (Boolean.FALSE.equals(validarCuenta(movimiento.getCuenta()))) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL_MOVIMIENTO);
        }

        return true;
    }
}
