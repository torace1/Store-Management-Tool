package com.smt.project.security.impl;

import com.smt.project.dto.request.SignUpRequest;
import com.smt.project.dto.request.SigninRequest;
import com.smt.project.dto.response.JwtAuthenticationResponse;
import com.smt.project.email.EmailSender;
import com.smt.project.enums.Role;
import com.smt.project.exception.SmtException;
import com.smt.project.model.User;
import com.smt.project.repository.UserRepository;
import com.smt.project.security.AuthenticationService;
import com.smt.project.security.JwtService;
import com.smt.project.util.Constants;
import com.smt.project.util.ValidationCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;



@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailSender emailSender;

    @Override
    public void signup(SignUpRequest request) {
        User user = new User();
        Optional<User> existingUser = userRepository.findByUsername(request.email());
        if (existingUser.isPresent()) {
            throw new SmtException(400, "This email is used");
        }
        user.setUsername(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.valueOf(request.role()));
        user.setEnabled(false);
        String validationCode= ValidationCodeUtil.generateValidationCode();
        user.setValidationCode(validationCode);
        sendEmailForConfirmation(request.email(),validationCode);
        userRepository.save(user);
        log.info(String.format("User %s with code : %s just signed up at %s", user.getUsername(), validationCode, System.currentTimeMillis()));
    }


    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {

        User user = userRepository.findByUsername(request.email())
                .orElseThrow(() -> new SmtException(404, "Invalid email or password."));
        if (!user.isEnabled()) {
            throw new SmtException(400, "User is not activated");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public void enableUser(String validationCode) {
        User user = userRepository.findByValidationCode(validationCode)
                .orElseThrow(() -> new SmtException(404, "User with this validationCode was not found"));
        if(user.isEnabled()){
            throw new SmtException(500, ("This code was already used"));
        }
        user.setEnabled(true);
        userRepository.save(user);
        log.info(String.format("User with validationCode : %s was enabled", validationCode));
    }

    private void sendEmailForConfirmation(String email, String validationCode) {

        String subject = "Please activate your account";
        String urlToBeEnabled = String.format(Constants.EmailContent.CONFIRMATION_URL, validationCode);
        String body = String.format(Constants.EmailContent.CONFIRMATION_CONTENT, urlToBeEnabled);

        log.info(String.format("Email was sent for %s", email));
        emailSender.sendEmail(email, subject, body);
    }

}
