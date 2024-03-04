package com.smt.project.repository;

import com.smt.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String email);
    Optional<User> findById(UUID id);

    Optional<User> findByValidationCode(String validationCode);
}
