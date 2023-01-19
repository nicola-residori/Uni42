package com.uni42.verticalapi.handler;

import com.uni42.verticalapi.exception.CustomerNotFoundException;
import com.uni42.verticalapi.exception.DuplicateCustomerException;
import com.uni42.verticalapi.model.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String RESOURCE = "Customer";

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateConfiguratorException(DuplicateCustomerException ex) {
        /* - prepare response - */
        ErrorResponse error = ErrorResponse.builder().resource(RESOURCE).type("DUPLICATE").message(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleConfiguratorNotFoundException(CustomerNotFoundException ex) {
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
