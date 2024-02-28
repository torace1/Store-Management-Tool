package com.smt.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(SmtException.class)
    public ResponseEntity<Object> handleRequestException(SmtException smtException){
        HttpStatus httpStatus;

        Integer errorCode = smtException.getErrorCode();
        httpStatus = switch (smtException.getErrorCode()) {
            case 400 -> HttpStatus.BAD_REQUEST;
            case 401 -> HttpStatus.UNAUTHORIZED;
            case 404 -> HttpStatus.NOT_FOUND;
            case 500 -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> throw new SmtException(500, String.format("Unexpected value of error code :  {%s}", errorCode));
        };
        return ResponseEntity.status(httpStatus).body(new SmtExceptionResponse(smtException));
    }
}
