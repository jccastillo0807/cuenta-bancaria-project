package co.com.banco.model.common.ex;

import java.util.function.Supplier;

public class BusinessException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public enum Type {
        INVALID_TODO_INITIAL_DATA("Invalid TODO initial data"),
        ERROR_CAMPO_NULL("Se detecto un campo vacio al validar el cuerpo de la solicitud. ¡Por favor revisar!"),
        ERROR_BASE_DATOS("Error en base de datos"),
        SALDO_INFERIOR_CERO("Saldo no disponible"),
        CUENTA_NO_ENCONTRADA("La cuenta NO EXISTE!"), ;

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
