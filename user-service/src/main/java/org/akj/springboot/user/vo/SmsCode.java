package org.akj.springboot.user.vo;

import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class SmsCode {
	private String code;

	private ZonedDateTime expiresAt;

	private String remark = "This is for testing purpose since has not integrated with SMS interface.";

	private SmsCode(String code, ZonedDateTime expiresAt) {
		this.code = code;
		this.expiresAt = expiresAt;
	}

	public static SmsCode of(String code, ZonedDateTime expiresAt) {
		return new SmsCode(code, expiresAt);
	}
}
