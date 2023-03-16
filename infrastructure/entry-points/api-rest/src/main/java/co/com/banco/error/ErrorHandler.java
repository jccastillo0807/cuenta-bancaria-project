package co.com.banco.error;

import co.com.banco.model.common.ex.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Clase que gestiona las excepciones mostradas durante el consumo de la API 
 *
 */
@ControllerAdvice
public class ErrorHandler {

    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<String> handleAllExceptions(Exception exception) {
        LOGGER.error("Error: ", exception);
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);        
    }

    
    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<String> handleBusinessExceptions(BusinessException exception) {
        LOGGER.error("BusinessException: ", exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
