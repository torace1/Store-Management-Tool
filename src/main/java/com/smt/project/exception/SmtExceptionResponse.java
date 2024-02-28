package com.smt.project.exception;

import lombok.Data;

@Data
public class SmtExceptionResponse {
    private String errorMessage;

    public SmtExceptionResponse(SmtException smtException) {
        this.errorMessage = smtException.getErrorMessage();
    }
}
