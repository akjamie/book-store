package org.akj.springboot.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.akj.springboot.auth.JwtTokenUtils;
import org.akj.springboot.auth.security.JwtAuthenticationEntryPoint;
import org.akj.springboot.auth.security.filter.JwtAuthenticationExceptionFilter;
import org.akj.springboot.auth.security.filter.JwtTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class GenericSecurityConfig {
	private final ObjectMapper objectMapper;

	private final JwtTokenUtils jwtTokenUtils;
	private final HandlerExceptionResolver handlerExceptionResolver;

	private final RedisTemplate redisTemplate;

	public GenericSecurityConfig(ObjectMapper objectMapper,
								 JwtTokenUtils jwtTokenUtils,
								 HandlerExceptionResolver handlerExceptionResolver,
								 RedisTemplate redisTemplate) {
		this.objectMapper = objectMapper;
		this.jwtTokenUtils = jwtTokenUtils;
		this.handlerExceptionResolver = handlerExceptionResolver;
		this.redisTemplate = redisTemplate;
	}

	@Bean
	public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() {
		return new JwtTokenAuthenticationFilter(jwtTokenUtils, redisTemplate, objectMapper);
	}

	@Bean
	public JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter() {
		return new JwtAuthenticationExceptionFilter(objectMapper);
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint(handlerExceptionResolver);
	}
}
