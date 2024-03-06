package com.smt.project.dto.request;


import com.smt.project.util.ValidationFields;

public record SigninRequest(String email, String password) {

    public SigninRequest {
        ValidationFields.checkEmailAndPassword(email,password);
    }
}
