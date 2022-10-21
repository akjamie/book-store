package org.akj.springboot.user.security.provider;

import org.akj.springboot.common.exception.BusinessException;
import org.akj.springboot.user.domain.UserStatus;
import org.akj.springboot.user.exception.BadCredentialsException;
import org.akj.springboot.user.exception.ErrorCodeMap;
import org.akj.springboot.user.service.UserService;
import org.akj.springboot.user.vo.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.akj.springboot.user.exception.ErrorCodeMap.*;

@Component
@Qualifier("passwordAuthenticationProvider")
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
	private final PasswordEncoder passwordEncoder;

	private final UserService userService;

	public UsernamePasswordAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException(BAD_LOGIN_CREDENTIALS.code(), BAD_LOGIN_CREDENTIALS.message());
		}

		UserDetails user = userService.loadUserByUsername(username);
		if (user.getPrincipal().getStatus() != UserStatus.ACTIVE) {
			throw new BusinessException(USER_LOGIN_NOT_ALLOWED.code(), USER_LOGIN_NOT_ALLOWED.message());
		}

		if (user == null || !passwordEncoder.matches(password, user.getPrincipal().getPassword())) {
			throw new BadCredentialsException(BAD_LOGIN_CREDENTIALS.code(), BAD_LOGIN_CREDENTIALS.message());
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
