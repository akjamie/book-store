package org.akj.springboot.auth;

public enum ErrorCodeMap {
	INVALID_AUTH_TOKEN("100-010", "Invalid auth token."),
	UNAUTHENTICATED("100-013", "Not authenticated.");

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
