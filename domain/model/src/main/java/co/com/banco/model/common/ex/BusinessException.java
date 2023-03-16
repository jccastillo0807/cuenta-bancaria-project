package co.com.banco.model.common.ex;

import java.util.function.Supplier;

public class BusinessException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public enum Type {
        INVALID_TODO_INITIAL_DATA("Invalid TODO initial data"),
        ERROR_CAMPO_NULL("Se detecto un campo vacio al validar el cuerpo de la solicitud. ¡Por favor revisar!"),
        ERROR_BASE_DATOS("Error en base de datos"),
        SALDO_INFERIOR_CERO("Saldo no disponible"),
        CUENTA_NO_ENCONTRADA("La cuenta NO EXISTE!"),
        ERROR_CAMPO_NULL_CUENTA("Se detecto un campo vacio, por favor validar los datos de la cuenta"),
        ERROR_CAMPO_NULL_CLIENTE("Se detecto un campo vacio, por favor validar los datos del cliente"),
        ERROR_CAMPO_NULL_MOVIMIENTO("Se detecto un campo vacio, por favor validar los datos del movimiento"),
        ERROR_CAMPO_NULL_PERSONA("Se detecto un campo vacio, por favor validar los datos de la persona"),
        ERROR_PERSONA_NO_REGISTRADA("La Persona no se encuentra registrada, registrar primero la persona"),
        ERROR_CLIENTE_NO_REGISTRADO("El cliente no se encuentra registrado, registrar primero el cliente"),
        MOVIMIENTO_NO_ENCONTRADO("Movimiento Bancario No Encontrado."),
        ERROR_CUENTA_NO_REGISTRADO("La Cuenta no se encuentra registrada, registrar primero la cuenta.");

        private final String message;

        public String getMessage() {
            return message;
        }

        public BusinessException build() {
            return new BusinessException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new BusinessException(this);
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final Type type;

    public BusinessException(Type type){
        super(type.message);
        this.type = type;
    }

    public BusinessException(Type type,String menssage){
        super(menssage);
        this.type = type;
    }

    @Override
    public String getCode(){
        return type.name();
    }


}
