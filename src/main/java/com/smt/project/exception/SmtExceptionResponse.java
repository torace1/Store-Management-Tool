package com.smt.project.exception;

public record SmtExceptionResponse(String errorMessage) {

    public SmtExceptionResponse(SmtException smtException) {
        this(smtException.getErrorMessage());
    }
}
