package com.smt.project.controller;

import com.smt.project.dto.response.AuditResponse;
import com.smt.project.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/audits")
@RestController
public class AuditController {

     private final AuditService auditService;

    @GetMapping("/{userId}")
    @Secured("ADMIN")
    public ResponseEntity<List<AuditResponse>> getAllAuditsByUser(@PathVariable UUID userId) {
        return  ResponseEntity.status(HttpStatus.OK).body(auditService.getAuditsByUserId(userId));
    }
}
