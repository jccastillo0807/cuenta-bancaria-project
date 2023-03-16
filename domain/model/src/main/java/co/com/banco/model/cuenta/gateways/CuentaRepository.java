package co.com.banco.model.cuenta.gateways;

import co.com.banco.model.cuenta.Cuenta;

import java.util.List;

public interface CuentaRepository {
    List<Cuenta> encontrarCuentas();

    Cuenta encontrarCuentaPorId(Integer id);

    Cuenta guardarCuenta(Cuenta cuenta);

}
