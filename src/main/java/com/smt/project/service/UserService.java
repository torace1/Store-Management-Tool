package com.smt.project.service;

import com.smt.project.model.User;
import com.smt.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getIdentityWithEmail(String email) {
        return userRepository.findByUsername(email)
                .orElse(null);
    }

//    public boolean verifyPassword(String username, String password) {
//        Optional<Identity> user = identityRepository.findByEmail(username);
//        if (user.isPresent() ) {
//            return passwordEncoder.matches(password, user.getPassword());
//        }
//        return false;
//    }

}
