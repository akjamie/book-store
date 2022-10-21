package org.akj.springboot.auth.config;

import org.akj.springboot.auth.security.JwtAuthenticationEntryPoint;
import org.akj.springboot.auth.security.filter.JwtAuthenticationExceptionFilter;
import org.akj.springboot.auth.security.filter.JwtTokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	@Autowired
	private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	JwtAuthenticationExceptionFilter authenticationExceptionFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.headers().frameOptions().disable()
				//access control for endpoints
				.and().authorizeRequests()
				.antMatchers("/h2-console/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator",
						"/actuator/**").permitAll()
				.anyRequest().authenticated()
				// filters
				.and().addFilterBefore(jwtTokenAuthenticationFilter, LogoutFilter.class)
				.addFilterBefore(authenticationExceptionFilter, CorsFilter.class)
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);

		return http.build();
	}

}