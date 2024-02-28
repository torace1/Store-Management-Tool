package com.smt.project.security.impl;

import com.smt.project.dto.JwtAuthenticationResponse;
import com.smt.project.dto.SignUpRequest;
import com.smt.project.dto.SigninRequest;
import com.smt.project.enums.Role;
import com.smt.project.exception.SmtException;
import com.smt.project.model.User;
import com.smt.project.repository.UserRepository;
import com.smt.project.security.AuthenticationService;
import com.smt.project.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public void signup(SignUpRequest request) {
        User user =  new User();
      Optional<User> existingUser=  userRepository.findByUsername(request.getEmail());
      if(existingUser.isPresent()) {
                throw  new SmtException(400, "This email is used");
      }
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRole()));
        user.setEnabled(false);
        userRepository.save(user);

    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        }
        catch (Exception e){
            throw new SmtException(400, "User is not activated");
        }
        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new SmtException(400,"Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
