package co.com.banco.model.common.ex;

import java.util.function.Supplier;

public class BusinessException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public enum Type {
        INVALID_TODO_INITIAL_DATA("Invalid TODO initial data"),
        INVALID_TERCERO("No fue posible crear el tercero"),
        INVALID_SEARCH_TERCERO("No fue posible encontrar el tercero"),
        INVALID_SEARCH_TERCERO_SOLICITUD_PRINCIPAL("Se puede proceder a diligenciar los campos del tercero"),
        ERROR_SEARCH_TERCERO("Ocurrio un error inesperado en la busqueda de terceros"),
        FILTER_NOT_FOUND("Informacion incompleta: Debe existir al menos un filtro"),
        INFO_BY("Informacion Incompleta: informacionPor Es obligatorio"),
        INFO_BY_INVALID("Informacion Invalida: El valor debe ser TERCERO, PROVEEDOR, ADMIN_CONTRATO, o CONSULTA"),
        ESTADO_INVALID("Informacion Invalida: El estado no es correcto!"),
        ESTADO_INACTIVO_INVALID("Informacion Invalida: El estado NO es valido para la busqueda."),
        ESTADO_REQUIRED("Informacion Incompleta: El estado es un campo Obligatorio!"),
        ERROR_COMUNICATION("Ocurrio un error Realizando la Comunicacion con IDM"),
        FINISH_DATE_REQUIRED("Informacion Incompleta: fechaTerminacionContrato es Obligatorio."),
        SURA_ACCESS_REQUIRED("Informacion Incompleta: accesoSura es Obligatorio."),
        BUSINESS_ROL_REQUIRED("Informacion Incompleta: rol de negocio es Obligatorio."),
        BUSINESS_ROL_INVALID("Informacion Invalida: rol de negocio no es obligatorio si no tiene Acceso SURA."),
        INVALID_PROVEEDOR("No fue posible crear el proveedor"),
        NOT_FOUND_PROVEEDOR("No se encontró el nombre o razón " +
                "social del proveedor en nuestra base de datos, favor diligenciarlo para " +
                "continuar con el registro"),
        ERROR_PROVEEDOR("Ocurrio un error buscando el proveedor en la base de datos"),
        INVALID_ADMIN_CONTRATO("No fue posible crear el admin contrato"),
        INVALID_SOLICITUD("No fue posible crear la solicitud"),
        INVALID_SOLICITUD_UPDATE("No fue posible actualizar la solicitud"),
        INVALID_TERCERO_SOLICITUD("El tercero ya se encuentra activo en nuestra base de datos. Verifique la informacion ingresada del proveedor o administrador de contrato."),
        SOLICITUD_REQUIRED("Informacion Incompleta: La SolicitudId es un campo Obligatorio."),
        PERSONA_ALREADY_EXIST("Esta persona ya existe"),
        FINISH_DATE_CONTRACT_INVALID("La fecha de terminacion del contrato es anterior a la fecha de hoy!"),
        EMAIL_INVALID("El correo electronico no cuentan con un formato valido"),
        ERROR_COMUNICATION_GETTOKEN_ADMINC("Ocurrio un error obteniendo el Token para consultar administrador del contrato."),
        ERROR_COMUNICATION_ADMINC("Ocurrio un error consultando la informacion del administrador del contrato."),
        ERROR_COMUNICATION_ADMINC_DOC("La informacion del Administrador del contrato no fue encontrada."),
        TIPO_DOCUMENTO_REQUIRED("Informacion Incompleta: Tipo de Identificacion es Obligatorio."),
        TIPO_DOCUMENTO_INVALID("Informacion Incorrecta: Tipo de Identificacion no es valido."),
        NUMERO_DOCUMENTO_REQUIRED("Informacion Incompleta: Numero de Identificacion es Obligatorio."),
        RAZON_SOCIAL_REQUIRED("Informacion Incompleta: Razon Social es Obligatorio."),
        EMAIL_REQUIRED("Informacion Incompleta: Correo Electronico es Obligatorio."),
        CARGO_REQUIRED("Informacion Incompleta: Cargo es Obligatorio."),
        PERSON_REQUIRED("Informacion Incompleta: Datos de la Persona es Obligatorio."),
        CODIGO_IPS_REQUIRED("Informacion Incompleta: CodigoIPS es Obligatorio."),

        CODIGO_IPS_NOT_FOUND("El Codigo IPS no existe"),
        CELULAR_REQUIRED("Informacion Incompleta: Celular es Obligatorio."),
        CELULAR_INVALID("Informacion Invalida: Celular no es valido."),
        NUMERO_CONTRATO_REQUIRED("Informacion Incompleta: Numero de Contrato es Obligatorio."),
        PRIMER_NOMBRE_REQUIRED("Informacion Incompleta: Primer Nombre es Obligatorio."),
        PRIMER_APELLIDO_REQUIRED("Informacion Incompleta: Primer Apellido es Obligatorio."),
        SEXO_REQUIRED("Informacion Incompleta: Sexo es Obligatorio."),
        NUMERO_DOCUMENTO_INVALID("Informacion Invalida: Numero de Identificacion no es valido."),
        SOLICITUD_NO_FOUND("Solicitud no encontrada"),
        ETL_NOT_FOUND("El ETL no fue encontrada"),
        ERROR_COMUNICATION_PROVEEDOR("Ocurrio un error Consultando la informacion del Proveedor"),
        ERROR_COMUNICATION_APUS("Ocurrio un error aprovisionando el tercero"),
        INVALID_UPDATE_ESTADO("El Estado no se pudo actualizar."),
        MENSAJE_RECHAZO_INVALID("Informacion Invalida: El mensaje de rechazo Solo es valido para estado Rechazado o Pendiente Corrección."),
        MENSAJE_RECHAZO_REQUIRED("Informacion Incompleta: El mensaje de rechazo es un campo obligatorio."),
        SOLICITUD_IDENTICA_ERROR("El tercero ya se encuentra activo en nuestra base de datos. Verifique la información ingresada del administrador de contrato y del proveedor."),
        PRIMER_NOMBRE_INVALID("Informacion Invalida: el primer nombre no es valido."),
        SEGUNDO_NOMBRE_INVALID("Informacion Invalida: el segundo nombre no es valido."),
        PRIMER_APELLIDO_INVALID("Informacion Invalida: el primer apellido no es valido."),
        ARCHIVO_VACIO("El archivo se encuentra vacio"),
        ERORR_ARCHIVO_REGISTRO_MASIVO("Ocurrio un error al procesar el Archivo"),
        HOJA_EXCEL_INEXISTENTE("La hoja de Excel no existe"),
        SEGUNDO_APELLIDO_INVALID("Informacion Invalida: el segundo apellido no es valido."),
        INVALID_SEARCH_ETL("No fue posible encontrar el ETL del usuario"),
        GENERATE_FILE_ERROR("Ocurrio un error generando el archivo"),
        ERROR_FORMAT_DATE("Error en la conversion de la fecha"),
        ERROR_EJECUCION_SOLICITUDES_MASIVO("Ocurrio un Error al enviar el comando de solicitudes Masivo."),
        ERROR_APROVISIONAMIENTO("Ocurrio un error en el aprovisionamiento de usuarios."),
        ADMINC_NOT_FOUND("No se encontro el Administrador de Contrato"),
        ERROR_PENDIENTE("Ocurrio un error inesperado en la busqueda del pendiente!"),
        ERROR_PAGE_ZERO("La pagina no puede ser menor que 0"),
        SEGUNDO_APELLIDO_REQUIRED("Informacion Incompleta: Segundo Apellido es Obligatorio."),
        TIPO_SOLICITUD_INVALIDO("Informacion Invalida: El tipo de la solicitud no es correcto!"),
        ROL_NO_FOUND("Rol no encontrado"),
        ROL_ID_REQUIRED("Información Incompleta: El Id del Rol es Obligatorio."),
        ROL_DESCRIPCION_REQUIRED("Información Incompleta: La Descripción del Rol es Obligatorio."),
        ROL_IDENTICO_ERROR("El rol ya se encuentra en nuestra base de datos. Verifique la información ingresada"),

        ERROR_CREAR_ROL("Error al crear el Rol"),

        ERROR_ACTUALIZAR_ROL("Error al actualizar el Rol"),
        ERROR_ELIMINAR_ROL("El Rol ha eliminar no se encuentra en nuestra base de datos. Verifique la información ingresada"),
        ROL_EXITOSO("¡El Rol se ha creado con éxito!"),
        ERROR_LISTA_ROL("¡Ocurrio un error inesperado en la búsqueda del rol!");

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
