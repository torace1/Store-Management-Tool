package com.smt.project.exception;

import lombok.Getter;

@Getter
public class SmtException extends  RuntimeException {

    private Integer errorCode;
    private String errorMessage;

    public SmtException(int errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage=errorMessage;
    }

    public SmtException(String errorMessage, Throwable cause){
        super(errorMessage,cause);
    }
}
