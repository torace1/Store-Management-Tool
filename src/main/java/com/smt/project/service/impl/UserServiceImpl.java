package com.smt.project.service.impl;

import com.smt.project.exception.SmtException;
import com.smt.project.model.User;
import com.smt.project.repository.UserRepository;
import com.smt.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new SmtException(404,"User not found"));
    }

    public User getUserWithEmail(String email) {
        return userRepository.findByUsername(email)
                .orElse(null);
    }

}
