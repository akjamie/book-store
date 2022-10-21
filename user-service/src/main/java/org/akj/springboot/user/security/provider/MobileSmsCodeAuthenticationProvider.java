package org.akj.springboot.user.security.provider;

import org.akj.springboot.common.exception.BusinessException;
import org.akj.springboot.user.domain.UserStatus;
import org.akj.springboot.user.exception.BadCredentialsException;
import org.akj.springboot.user.exception.ErrorCodeMap;
import org.akj.springboot.user.service.SmsCodeService;
import org.akj.springboot.user.service.UserService;
import org.akj.springboot.user.vo.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.akj.springboot.user.exception.ErrorCodeMap.USER_LOGIN_NOT_ALLOWED;

@Component
@Qualifier("smsCodeAuthenticationProvider")
public class MobileSmsCodeAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;

	private final UserService userService;

	private final SmsCodeService smsCodeService;

	public MobileSmsCodeAuthenticationProvider(PasswordEncoder passwordEncoder,
											   UserService userService, SmsCodeService smsCodeService) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.smsCodeService = smsCodeService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String phone = (String) authentication.getPrincipal();
		String smsCode = (String) authentication.getCredentials();

		if (StringUtils.isBlank(phone) || StringUtils.isBlank(smsCode)) {
			throw new BadCredentialsException(ErrorCodeMap.BAD_LOGIN_CREDENTIALS_MOBILE_SMSCODE.code(),
					ErrorCodeMap.BAD_LOGIN_CREDENTIALS_MOBILE_SMSCODE.message());
		}

		// obtain sms code from redis & verify
		if (!smsCodeService.verifySmsCode(phone, smsCode)) {
			throw new BadCredentialsException(ErrorCodeMap.BAD_LOGIN_CREDENTIALS_MOBILE_SMSCODE.code(),
					ErrorCodeMap.BAD_LOGIN_CREDENTIALS_MOBILE_SMSCODE.message());
		}

		UserDetails user = userService.loadUserByPhoneNumber(phone);
		if (user.getPrincipal().getStatus() != UserStatus.ACTIVE) {
			throw new BusinessException(USER_LOGIN_NOT_ALLOWED.code(), USER_LOGIN_NOT_ALLOWED.message());
		}
		if (user == null) {
			throw new BusinessException(ErrorCodeMap.BAD_LOGIN_CREDENTIALS_MOBILE_SMSCODE.code(),
					ErrorCodeMap.BAD_LOGIN_CREDENTIALS_MOBILE_SMSCODE.message());
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return MobileSmsCodeAuthenticationToken.authenticated(user, smsCode, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (MobileSmsCodeAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
