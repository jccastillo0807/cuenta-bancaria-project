package co.com.banco.usecase.cuenta;

import co.com.banco.model.common.StringUtils;
import co.com.banco.model.common.ex.BusinessException;
import co.com.banco.model.cuenta.Cuenta;
import co.com.banco.model.cuenta.gateways.CuentaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CuentaUseCase {

    public static final String INACTIVO = "INACTIVO";
    private final CuentaRepository cuentaRepository;

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
        if (validacionCamposCuenta) {
            return cuentaRepository.guardarCuenta(cuenta);
        }
        throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL);
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

    private Boolean validarCuenta(Cuenta cuenta) {
        if (cuenta.getId() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL);
        }
        if (StringUtils.isEmpty(cuenta.getNumeroCuenta())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL);
        }
        if (StringUtils.isEmpty(cuenta.getTipoCuenta())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL);
        }
        if (cuenta.getSaldoInicial() == null) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL);
        }
        if (StringUtils.isEmpty(cuenta.getEstado())) {
            throw new BusinessException(BusinessException.Type.ERROR_CAMPO_NULL);
        }
        return true;
    }
}
