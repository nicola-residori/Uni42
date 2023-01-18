package com.uni42.channelapi.handler;

import com.uni42.channelapi.exception.UserNotAuthorizedException;
import com.uni42.channelapi.model.ErrorResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String RESOURCE = "Profile";

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        /* - prepare response - */
        ErrorResponse error = ErrorResponse.builder().resource(RESOURCE).type("UNAUTHORIZED").message(ex.getMessage()).timestamp(new Date()).build();
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        /* - prepare response - */
        ErrorResponse error = ErrorResponse.builder().resource(RESOURCE).type("GENERIC").message(ExceptionUtils.getStackTrace(ex)).timestamp(new Date()).build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
