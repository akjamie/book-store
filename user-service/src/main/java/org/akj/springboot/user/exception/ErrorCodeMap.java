package org.akj.springboot.user.exception;

public enum ErrorCodeMap {
    BAD_LOGIN_CREDENTIALS("100-001", "Incorrect username or password."),
    USER_NOT_EXIST("100-002", "User does not exist."),
    TECH_NO_AUTH_PROVIDER_FOUND("100-003", "No authentication provider found."),
    INVALID_PHONE_NUMBER("100-004", "Invalid phone number."),
    INVALID_EMAIL_ADDRESS("100-005", "Invalid email address."),
    INCORRECT_SMSCODE("100-006", "Verification code is incorrect."),
    BAD_LOGIN_CREDENTIALS_MOBILE_SMSCODE("100-007", "Incorrect mobile phone number or verification code."),
    SMSCODE_ALREADY_SENT("100-008", "Sms verification code has already sent, please check it on your mobile."),
    ILLEGAL_TOKEN_CONTENT("100-009", "Illegal auth token content."),
    INVALID_AUTH_TOKEN("100-010", "Invalid auth token."),
    AUTHENTICATION_FAILED("100-012", "Authentication failed."),
    UNAUTHENTICATED("100-013", "Not authenticated."),
    ACCESS_DENIED("100-014", "Access denied."),
    USER_LOGIN_NOT_ALLOWED("100-015", "User is allowed to login."),
    ;

    private String code;
    private String message;

    ErrorCodeMap(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
