package com.smt.project.repository;

import com.smt.project.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();

    User getIdentityWithEmail(String email);
}
