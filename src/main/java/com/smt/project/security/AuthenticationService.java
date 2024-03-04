package com.smt.project.security;

import com.smt.project.dto.request.SignUpRequest;
import com.smt.project.dto.request.SigninRequest;
import com.smt.project.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    void signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    void enableUser(String userEmail);
}
