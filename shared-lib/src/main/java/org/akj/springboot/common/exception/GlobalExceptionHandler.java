package org.akj.springboot.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.common.constant.ErrorCodeConstant;
import org.akj.springboot.common.domain.Result;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE + 100)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest httpServletRequest,
												  HttpServletResponse httpServletResponse) {
		log.error("method:{}, message:{}.", "handleException", ex.getMessage(), ex);
		ServletWebRequest request = new ServletWebRequest(httpServletRequest);

		return handleExceptionInternal(ex, null, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(TechnicalException.class)
	@ResponseBody
	public ResponseEntity<Object> handleTechnicalException(TechnicalException ex, HttpServletResponse httpServletResponse) {
		Result response = Result.failure(ex.getCode(),
				null == ex.getMessage() || ex.getMessage().trim().isEmpty() ? ExceptionUtils.getStackTrace(ex) : ex.getMessage());

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, HttpServletResponse httpServletResponse) {
		Result response = Result.failure(ex.getCode(),
				null == ex.getMessage() || ex.getMessage().trim().isEmpty() ? ExceptionUtils.getStackTrace(ex) : ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}


	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
															 HttpStatusCode status, WebRequest request) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
		}

		// construct base response object
		Result response = Result.failure(ErrorCodeConstant.TECHNICAL_EXCEPTION,
				null == ex.getMessage() || ex.getMessage().trim().isEmpty() ? ExceptionUtils.getStackTrace(ex) : ex.getMessage(),
				body);

		return new ResponseEntity<>(response, headers, status);
	}
}
