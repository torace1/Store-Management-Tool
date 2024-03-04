package com.smt.project.controller;

import com.smt.project.dto.request.SignUpRequest;
import com.smt.project.dto.request.SigninRequest;
import com.smt.project.dto.response.JwtAuthenticationResponse;
import com.smt.project.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest request) {
        authenticationService.signup(request);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SigninRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }

    @GetMapping("/enable")
    public ResponseEntity<Void> enableUser(@RequestParam String validationCode) {
        authenticationService.enableUser(validationCode);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
