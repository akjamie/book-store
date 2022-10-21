package org.akj.springboot.user.domain;

public enum AuthType {
    PASSWORD("password", "Authenticate with username/password"),
    MOBILE("mobile", "Authenticate with mobile number/sms code"),
    EMAIL("email", "Authenticate with email/verification code");

    private String type;

    private String desc;

    AuthType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
