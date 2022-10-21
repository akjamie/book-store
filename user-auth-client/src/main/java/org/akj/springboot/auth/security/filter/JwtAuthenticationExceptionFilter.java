package org.akj.springboot.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.common.domain.Result;
import org.akj.springboot.common.exception.BusinessException;
import org.akj.springboot.common.exception.TechnicalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JwtAuthenticationExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper mapper;

    public JwtAuthenticationExceptionFilter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("doing filter for custom auth exceptions.");
        try {
            filterChain.doFilter(request, response);
        } catch (BusinessException | TechnicalException ex) {
            log.error("An exception has been caught by filters: ", ex);

            response.setContentType(MediaType.APPLICATION_JSON.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            Result result = Result.failure(ex.getCode(), ex.getMessage());

            PrintWriter writer = response.getWriter();
            writer.write(mapper.writeValueAsString(result));
            writer.close();
        }
    }
}
