package com.smt.project.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SigninRequest {
    private String email;
    private String password;
}
