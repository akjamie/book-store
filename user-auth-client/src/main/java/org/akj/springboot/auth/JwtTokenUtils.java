package org.akj.springboot.auth;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.akj.springboot.auth.vo.UserToken;
import org.akj.springboot.common.exception.TechnicalException;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtException;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.akj.springboot.auth.ErrorCodeMap.INVALID_AUTH_TOKEN;

public class JwtTokenUtils {

	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;

	private final JWSVerifier jwsVerifier;


	public JwtTokenUtils(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, JWSVerifier jwsVerifier) {
		this.jwtEncoder = jwtEncoder;
		this.jwtDecoder = jwtDecoder;
		this.jwsVerifier = jwsVerifier;
	}

	@SneakyThrows
	public boolean checkTokenValidity(@NonNull String token) {

		// verify token
		SignedJWT signedJWT = SignedJWT.parse(token);
		boolean result = signedJWT.verify(jwsVerifier);
		if (!result) {
			throw new TechnicalException(INVALID_AUTH_TOKEN.code(), INVALID_AUTH_TOKEN.message());
		}

		try {
			// check if token expires
			Jwt jwt = jwtDecoder.decode(token);
		} catch (JwtException ex) {
			throw new TechnicalException(INVALID_AUTH_TOKEN.code(), INVALID_AUTH_TOKEN.message());
		}

		return Boolean.TRUE;
	}

	public UserToken convert(String token) {
		Jwt jwt = jwtDecoder.decode(token);
		return this.convert(jwt);
	}

	public UserToken convert(Jwt jwt) {
		return UserToken.builder()
				.issueTo(jwt.getSubject())
				.expiresAt(jwt.getExpiresAt())
				.claims(jwt.getClaims())
				.id(jwt.getId())
				.token(jwt.getTokenValue()).build();
	}

	public boolean isTokenExpired(String token) {
		Jwt jwt = jwtDecoder.decode(token);
		return checkTokenExpiry(jwt);
	}

	private boolean checkTokenExpiry(@NonNull Jwt jwt) {
		return jwt.getExpiresAt() != null &&
				LocalDateTime.ofInstant(jwt.getExpiresAt(), ZoneId.systemDefault()).isAfter(LocalDateTime.now());
	}

}
