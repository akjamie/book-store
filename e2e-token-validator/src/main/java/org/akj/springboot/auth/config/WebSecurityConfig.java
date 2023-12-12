package org.akj.springboot.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.akj.springboot.auth.JwtTokenUtils;
import org.akj.springboot.auth.security.JwtAuthenticationEntryPoint;
import org.akj.springboot.auth.security.filter.JwtAuthenticationExceptionFilter;
import org.akj.springboot.auth.security.filter.JwtTokenAuthenticationFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static org.springframework.core.Ordered.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfig {
	private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private final JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter;

	public WebSecurityConfig(JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter,
							 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
							 JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter) {
		this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAuthenticationExceptionFilter = jwtAuthenticationExceptionFilter;
	}

	@Bean
	//@Order(value = HIGHEST_PRECEDENCE - 1)
	@ConditionalOnMissingBean(SecurityFilterChain.class)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				//access control for endpoints
				.authorizeRequests(authorizeRequests ->
						authorizeRequests.requestMatchers("/h2-console/**",
								"/swagger-ui/**",
								"/swagger-ui.html",
								"/v3/api-docs",
								"/v3/api-docs/**",
								"/actuator",
								"/actuator/**"
						).permitAll().anyRequest().authenticated()
				)
				// filters
				.addFilterBefore(jwtTokenAuthenticationFilter, LogoutFilter.class)
				.addFilterBefore(jwtAuthenticationExceptionFilter, CorsFilter.class)
				.exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(jwtAuthenticationEntryPoint));

		return http.build();
	}

}