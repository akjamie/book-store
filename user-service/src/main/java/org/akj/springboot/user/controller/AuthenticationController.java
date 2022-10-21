package org.akj.springboot.user.controller;

import com.nimbusds.jose.jwk.JWK;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.common.exception.BusinessException;
import org.akj.springboot.common.domain.Result;
import org.akj.springboot.user.service.SmsCodeService;
import org.akj.springboot.user.vo.SmsCode;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static org.akj.springboot.user.exception.ErrorCodeMap.*;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
public class AuthenticationController {
	private static final String PHONE_NUMBER_REGEX =
			"^(?:(?:\\+|00)86)?1(?:3\\d|4[4-9]|5[0-35-9]|6[67]|7[013-8]|8\\d|9\\d)\\d{8}$";

	private final JWK jwk;

	private final SmsCodeService smsCodeService;

	public AuthenticationController(JWK jwk, SmsCodeService smsCodeService) {
		this.jwk = jwk;
		this.smsCodeService = smsCodeService;
	}

	@GetMapping("/public-cert")
	public Result getPublicCert() {
		return Result.success(jwk.toPublicJWK().toJSONObject());
	}

	@PutMapping("/sms-code")
	public Result sendSmsVerifyCode(@NotNull @RequestParam("phoneNumber") String phoneNumber) {
		log.info("Sending sms verification code, phone number: {}", phoneNumber);

		if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
			throw new BusinessException(INVALID_PHONE_NUMBER.code(), INVALID_PHONE_NUMBER.message());
		}

		if (smsCodeService.isSmsCodeAlreadySentAndValid(phoneNumber)) {
			throw new BusinessException(SMSCODE_ALREADY_SENT.code(), SMSCODE_ALREADY_SENT.message());
		}

		SmsCode smsCode = smsCodeService.generateAndSendSmsCode(phoneNumber);

		return Result.success(smsCode);
	}

	@PostMapping("/sms-code")
	public Result verifySmsCode(@NotNull @RequestParam("phoneNumber") String phoneNumber,
								@NotNull @RequestParam("code") String smsCode) {
		log.info("Verifying sms code, phone number: {}, code: {}", phoneNumber, smsCode);
		if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
			throw new BusinessException(INVALID_PHONE_NUMBER.code(), INVALID_PHONE_NUMBER.message());
		}

		if (!smsCodeService.verifySmsCode(phoneNumber, smsCode)) {
			throw new BusinessException(INCORRECT_SMSCODE.code(), INCORRECT_SMSCODE.message());
		}

		return Result.ok();
	}

}
