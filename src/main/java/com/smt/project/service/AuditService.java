package com.smt.project.service;

import com.smt.project.dto.response.AuditResponse;
import com.smt.project.model.Audit;

import java.util.List;
import java.util.UUID;

public interface AuditService {

    void saveAudit(Audit audit);

    List<AuditResponse> getAuditsByUserId(UUID userId);
}
