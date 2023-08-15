package org.akj.springboot.auth;

public final class SecurityConstant {
	private SecurityConstant() {
	}

	public static final String TOKEN_KEY_ID = "kid";
	public static final String TOKEN_USER_ID = "uid";
	public static final String TOKEN_AUTHORITIES = "scope";

	public static final String TOKEN_KEY_PATTERN = "token:auth:%s";
}
