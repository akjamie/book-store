package org.akj.springboot.user.constant;

public final class Constant {
    private Constant() {
    }

    // token:auth:{userid}
    public static final String TOKEN_KEY_PATTERN = "token:auth:%s";
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "phoneNumber";
    public static final String SPRING_SECURITY_FORM_SMSCODE_KEY = "code";

    public static final String DEFAULT_SIGN_IN_PROCESSING_URL_FORM = "/v1/auth/form";

    public static final String DEFAULT_SIGN_IN_PROCESSING_URL_SMS = "/v1/auth/sms";

    public static final String DEFAULT_SIGN_OUT_PROCESSING_URL = "/v1/auth/logout";
}
