package org.akj.springboot.user.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.akj.springboot.common.domain.Result;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex,
																HttpServletResponse httpServletResponse) {
		Result response = Result.failure(String.valueOf(HttpStatus.UNAUTHORIZED.value()),
				null == ex.getMessage() || ex.getMessage().trim().isEmpty() ? ExceptionUtils.getStackTrace(ex) : ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
}
