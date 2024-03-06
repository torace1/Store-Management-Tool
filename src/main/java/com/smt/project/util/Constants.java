package com.smt.project.util;

public  class Constants {

    public static class EmailContent {
        public static final String CONFIRMATION_CONTENT = "<p>To activate your account, please click on the following link:</p>" +
                "<a href=\" %s \">Click here to activate your account</a>";
        public static final String CONFIRMATION_URL = "http://localhost:8080/smt/auth/enable?validationCode=%s";


    }
    public static class Regex {
        public static final String EMAIL_CHECKER_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        public static final String PASSWORD_CHECKER_REGEX ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";


    }

}
