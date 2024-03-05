package com.smt.project.service.impl;

import com.smt.project.dto.response.AuditResponse;
import com.smt.project.model.Audit;
import com.smt.project.repository.AuditRepository;
import com.smt.project.service.AuditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void saveAudit(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public List<AuditResponse> getAuditsByUserId(UUID userId) {
        List<Audit>  audits = auditRepository.findByUserId(userId);
        List<AuditResponse> auditResponseList = new ArrayList<>();
        audits.forEach(audit -> {
            auditResponseList.add( AuditResponse.castAuditToResponse(audit));
        });
        return auditResponseList;
    }
}
