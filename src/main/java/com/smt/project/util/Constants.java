package com.smt.project.util;

public  class Constants {

    public static class EmailContent {
        public static final String CONFIRMATION_CONTENT = "<p>To activate your account, please click on the following link:</p>" +
                "<a href=\" %s \">Click here to activate your account</a>";
        public static final String CONFIRMATION_URL = "http://localhost:8080/smt/auth/enable?validationCode=%s";

    }

}
