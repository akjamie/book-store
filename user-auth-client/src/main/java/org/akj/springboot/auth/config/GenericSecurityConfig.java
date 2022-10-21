package org.akj.springboot.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.akj.springboot.auth.JwtTokenUtils;
import org.akj.springboot.auth.security.JwtAuthenticationEntryPoint;
import org.akj.springboot.auth.security.filter.JwtAuthenticationExceptionFilter;
import org.akj.springboot.auth.security.filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class GenericSecurityConfig {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;

	@Autowired
	private RedisTemplate redisTemplate;

	@Bean
	public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() {
		return new JwtTokenAuthenticationFilter(jwtTokenUtils, redisTemplate);
	}

	@Bean
	public JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter() {
		return new JwtAuthenticationExceptionFilter(objectMapper);
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint(objectMapper, handlerExceptionResolver);
	}
}
