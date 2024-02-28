package com.smt.project.dto;

import com.smt.project.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpRequest {

    private String email;
    private String password;
    private String role;
}
