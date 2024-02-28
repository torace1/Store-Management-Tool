package com.smt.project.security;

import com.smt.project.dto.JwtAuthenticationResponse;
import com.smt.project.dto.SignUpRequest;
import com.smt.project.dto.SigninRequest;

public interface AuthenticationService {
    void signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
