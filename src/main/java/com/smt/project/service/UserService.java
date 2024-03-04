package com.smt.project.service;

import com.smt.project.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    User getUserWithEmail(String email);
}
