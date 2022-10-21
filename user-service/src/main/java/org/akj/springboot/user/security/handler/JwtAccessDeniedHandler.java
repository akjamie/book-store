package org.akj.springboot.user.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.common.domain.Result;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.akj.springboot.user.exception.ErrorCodeMap.ACCESS_DENIED;

@Component
@Slf4j
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper mapper;

	public JwtAccessDeniedHandler(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		Result result = Result.failure(ACCESS_DENIED.code(), ACCESS_DENIED.message());
		PrintWriter writer = response.getWriter();
		writer.write(mapper.writeValueAsString(result));
		writer.close();
	}
}
