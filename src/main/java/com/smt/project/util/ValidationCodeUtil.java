package com.smt.project.util;

import java.util.Random;

public class ValidationCodeUtil {

    static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    static final int CODE_LENGTH = 14;

    public static String generateValidationCode() {
        Random random = new Random();
        StringBuilder validationCode = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            validationCode.append(CHARACTERS.charAt(randomIndex));
        }

        return validationCode.toString();
    }
}
