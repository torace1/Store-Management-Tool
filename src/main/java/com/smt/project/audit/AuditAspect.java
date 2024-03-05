package com.smt.project.audit;

import com.smt.project.model.Audit;
import com.smt.project.model.User;
import com.smt.project.security.JwtService;
import com.smt.project.service.AuditService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class AuditAspect {


    private final AuditService auditService;

    private final JwtService jwtService;

    @AfterReturning("@annotation(auditLogging) && execution(* *(..))")
    public void logAudit(JoinPoint joinPoint, AuditLogging auditLogging) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() ) {
            authentication.getDetails();
          User user= (User) authentication.getPrincipal();

            String methodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            String actionString = "Method " + methodName + " invoked with arguments: " + argsToString(args);
            Audit audit = new Audit() ;
            audit.setAction(actionString);
            audit.setUser(user);
            audit.setTimestamp(System.currentTimeMillis());
            auditService.saveAudit( audit);
        }
    }

    private String argsToString(Object[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object arg : args) {
            stringBuilder.append(arg).append(", ");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }
}
