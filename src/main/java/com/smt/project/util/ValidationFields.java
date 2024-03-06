package com.smt.project.util;

import com.smt.project.enums.Role;
import com.smt.project.exception.SmtException;

public class ValidationFields {

    public static void checkEmailAndPassword(String email, String password){
        if (email == null || !email.matches(Constants.Regex.EMAIL_CHECKER_REGEX)) {
            throw new SmtException(400, "Invalid email format");
        }
        if (password == null || !password.matches(Constants.Regex.PASSWORD_CHECKER_REGEX)) {
            throw new SmtException(400, "Password must be at least 8 characters long and contain at least one letter and one digit");
        }
    }

    public static void checkRole(String role){
        if (role != null && !isValidEnum(role, Role.class)) {
            throw new SmtException(400, "Role must be USER or ADMIN");
        } else if (role == null) {
            throw new SmtException(400, "Role must be provided");
        }
    }

    private static <T extends Enum<T>> boolean isValidEnum(String value, Class<T> enumClass) {
        if (value == null) {
            return false;
        }
        try {
            Enum.valueOf(enumClass, value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
