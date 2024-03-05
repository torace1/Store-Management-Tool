package com.smt.project.repository;

import com.smt.project.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditRepository extends JpaRepository<Audit, UUID> {

    List<Audit> findByUserId(UUID userId);
}
