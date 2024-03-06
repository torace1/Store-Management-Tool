package com.smt.project.dto.request;

import com.smt.project.util.ValidationFields;

public record SignUpRequest(String email, String password, String role) {

    public SignUpRequest {
        ValidationFields.checkEmailAndPassword(email,password);
        ValidationFields.checkRole(role);
    }
}
