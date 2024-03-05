package com.smt.project.dto.response;

import com.smt.project.model.Audit;
import lombok.Data;

import java.util.UUID;

@Data
public class AuditResponse {

    private UUID id;
    private String action;
    private UUID userId;
    private long timestamp;


    public static AuditResponse castAuditToResponse(Audit audit){
        AuditResponse auditResponse = new AuditResponse();
        auditResponse.setId(audit.getId());
        auditResponse.setAction(audit.getAction());
        auditResponse.setUserId(audit.getUser().getId());
        auditResponse.setTimestamp(audit.getTimestamp());
        return  auditResponse;
    }
}
