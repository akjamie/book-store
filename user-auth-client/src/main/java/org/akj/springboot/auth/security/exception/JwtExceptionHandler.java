package org.akj.springboot.auth.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.auth.ErrorCodeMap;
import org.akj.springboot.common.domain.Result;
import org.akj.springboot.common.exception.GlobalExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
@Slf4j
public class JwtExceptionHandler extends GlobalExceptionHandler {

	@ExceptionHandler(InsufficientAuthenticationException.class)
	@ResponseBody
	public ResponseEntity<Object> handleTechnicalException(InsufficientAuthenticationException ex, HttpServletResponse httpServletResponse) {
		Result response = Result.failure(ErrorCodeMap.UNAUTHENTICATED.code(), ErrorCodeMap.UNAUTHENTICATED.message());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public ResponseEntity<Object> handleTechnicalException(AuthenticationException ex, HttpServletResponse httpServletResponse) {
		Result response = Result.failure(ErrorCodeMap.UNAUTHENTICATED.code(), ErrorCodeMap.UNAUTHENTICATED.message());

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

}
