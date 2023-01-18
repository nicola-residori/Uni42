package com.uni42.configurator.handler;

import com.uni42.configurator.exception.ConfiguratorNotFoundException;
import com.uni42.configurator.exception.DuplicateConfiguratorException;
import com.uni42.configurator.model.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String RESOURCE = "Configurator";

    @ExceptionHandler(DuplicateConfiguratorException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateConfiguratorException(DuplicateConfiguratorException ex) {
        /* - prepare response - */
        ErrorResponse error = ErrorResponse.builder().resource(RESOURCE).type("DUPLICATE").message(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConfiguratorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConfiguratorNotFoundException(ConfiguratorNotFoundException ex) {
        /* - prepare response - */
        ErrorResponse error = ErrorResponse.builder().resource(RESOURCE).type("NOT_FOUND").message(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        /* - prepare response - */
        ErrorResponse error = ErrorResponse.builder().resource(RESOURCE).type("GENERIC").message(ExceptionUtils.getStackTrace(ex)).timestamp(new Date()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
