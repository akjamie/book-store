package org.akj.springboot.user.service;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.user.vo.SmsCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
@Slf4j
public class SmsCodeService {
	static final String SMS_CODE_TEMPLATE = "Your SMS verification code is %s, please be reminded it will expires within %d minutes " +
			"and only can be used once.";
	// sms:auth:{phoneNumber}
	public static final String SMS_CODE_REDIS_KEY_TEMPLATE = "sms:auth:%s";

	@Value("${auth.sms-code.max-digits:6}")
	private Integer smsCodeMaxDigits;

	@Value("${auth.sms-code.time-to-live:2}")
	private Long timeToLive;

	private final RedisTemplate redisTemplate;

	public SmsCodeService(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public SmsCode generateAndSendSmsCode(String phoneNumber) {

		// generate random digits code
		SmsCode smsCode = generateSmsCode();

		// assemble the sms message
		String code = smsCode.getCode();
		String sms = String.format(SMS_CODE_TEMPLATE, code, timeToLive);
		log.info("sms message is [{}]", sms);

		// call remote interface to send

		// record the ttl in redis
		String smsCodeKey = getSmsCodekey(phoneNumber);
		redisTemplate.opsForValue().set(smsCodeKey, code, timeToLive, TimeUnit.MINUTES);
		return smsCode;
	}

	public boolean isSmsCodeAlreadySentAndValid(String phoneNumber) {
		return redisTemplate.hasKey(getSmsCodekey(phoneNumber));
	}


	private SmsCode generateSmsCode() {
		Random random = new Random();
		String code = IntStream.range(0, smsCodeMaxDigits)
				.map(i -> random.nextInt(10))
				.mapToObj(i -> i + "")
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();

		log.debug("generated sms code is {}", code);

		return SmsCode.of(code, ZonedDateTime.now().plus(timeToLive, ChronoUnit.MINUTES));
	}

	private String getSmsCodekey(String phoneNumber) {
		return String.format(SMS_CODE_REDIS_KEY_TEMPLATE, phoneNumber);
	}

	public boolean verifySmsCode(String phoneNumber, String smsCode) {
		String key = getSmsCodekey(phoneNumber);
		if (StringUtils.isNotBlank(smsCode) && smsCode.length() == smsCodeMaxDigits && redisTemplate.hasKey(key)) {
			return redisTemplate.opsForValue().getAndDelete(key).equals(smsCode);
		}

		return false;
	}
}
