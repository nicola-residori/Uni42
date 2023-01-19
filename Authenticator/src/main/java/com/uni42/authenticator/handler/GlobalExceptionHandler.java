package com.uni42.authenticator.handler;

import com.uni42.authenticator.exception.DuplicateUserException;
import com.uni42.authenticator.exception.UserNotFoundException;
import com.uni42.authenticator.model.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String RESOURCE = "Authenticator";

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException ex) {
        /* - prepare response - */
        ErrorResponse error = ErrorResponse.builder().resource(RESOURCE).type("DUPLICATE").message(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
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
