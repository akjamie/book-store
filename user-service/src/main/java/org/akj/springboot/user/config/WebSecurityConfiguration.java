package org.akj.springboot.user.config;

import org.akj.springboot.auth.security.JwtAuthenticationEntryPoint;
import org.akj.springboot.auth.security.filter.JwtAuthenticationExceptionFilter;
import org.akj.springboot.auth.security.filter.JwtTokenAuthenticationFilter;
import org.akj.springboot.user.security.filter.MobileSmsCodeAuthenticationFilter;
import org.akj.springboot.user.security.handler.JwtAccessDeniedHandler;
import org.akj.springboot.user.security.handler.JwtLogoutSuccessHandler;
import org.akj.springboot.user.security.handler.JwtTokenAuthenticationFailureHandler;
import org.akj.springboot.user.security.handler.JwtTokenAuthenticationSuccessHandler;
import org.akj.springboot.user.security.provider.MobileSmsCodeAuthenticationProvider;
import org.akj.springboot.user.security.provider.UsernamePasswordAuthenticationProvider;
import org.akj.springboot.user.service.SmsCodeService;
import org.akj.springboot.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.akj.springboot.user.constant.Constant.DEFAULT_SIGN_IN_PROCESSING_URL_FORM;
import static org.akj.springboot.user.constant.Constant.DEFAULT_SIGN_OUT_PROCESSING_URL;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfiguration {
	private final UserService userService;

	private final SmsCodeService smsCodeService;

	private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

	private final JwtAuthenticationExceptionFilter authenticationExceptionFilter;
	private final JwtTokenAuthenticationSuccessHandler jwtTokenAuthenticationSuccessHandler;

	private final JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler;

	private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurityConfiguration(UserService userService,
									SmsCodeService smsCodeService,
									JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter,
									JwtAuthenticationExceptionFilter authenticationExceptionFilter,
									JwtTokenAuthenticationSuccessHandler jwtTokenAuthenticationSuccessHandler,
									JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler,
									JwtLogoutSuccessHandler jwtLogoutSuccessHandler,
									JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
									JwtAccessDeniedHandler jwtAccessDeniedHandler,
									BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.smsCodeService = smsCodeService;
		this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
		this.authenticationExceptionFilter = authenticationExceptionFilter;
		this.jwtTokenAuthenticationSuccessHandler = jwtTokenAuthenticationSuccessHandler;
		this.jwtTokenAuthenticationFailureHandler = jwtTokenAuthenticationFailureHandler;
		this.jwtLogoutSuccessHandler = jwtLogoutSuccessHandler;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				//access control for endpoints
				.authorizeRequests((authorizeRequests) ->
						authorizeRequests.requestMatchers("/h2-console/**",
								"/swagger-ui/**",
								"/swagger-ui.html",
								"/v3/api-docs/**",
								"/actuator",
								"/actuator/**",
								"/v1/auth/public-cert",
								"/v1/auth/sms-code",
								"/v1/auth/form",
								"/v1/errors"
						).permitAll().anyRequest().authenticated()
				)
				// form login
				.formLogin(form -> form.loginProcessingUrl(DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
						.failureHandler(jwtTokenAuthenticationFailureHandler)
						.successHandler(jwtTokenAuthenticationSuccessHandler)
						.permitAll())
				// logout
				.logout(logout -> logout.logoutUrl(DEFAULT_SIGN_OUT_PROCESSING_URL).logoutSuccessHandler(jwtLogoutSuccessHandler))
				// authentication providers
				.authenticationManager(authenticationManager())
				// filters
				.addFilterBefore(mobileSmsCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtTokenAuthenticationFilter, LogoutFilter.class)
				.addFilterBefore(authenticationExceptionFilter, CorsFilter.class)
				// exception handle
				.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
						.accessDeniedHandler(jwtAccessDeniedHandler));

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(List.of(usernamePasswordAuthenticationProvider(), mobileSmsCodeAuthenticationProvider()));
	}

	@Bean
	public MobileSmsCodeAuthenticationFilter mobileSmsCodeAuthenticationFilter() {
		MobileSmsCodeAuthenticationFilter filter = new MobileSmsCodeAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(jwtTokenAuthenticationSuccessHandler);
		filter.setAuthenticationFailureHandler(jwtTokenAuthenticationFailureHandler);
		return filter;
	}


	public UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider() {
		return new UsernamePasswordAuthenticationProvider(bCryptPasswordEncoder, userService);
	}

	public MobileSmsCodeAuthenticationProvider mobileSmsCodeAuthenticationProvider() {
		return new MobileSmsCodeAuthenticationProvider(bCryptPasswordEncoder, userService, smsCodeService);
	}

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source =
//                new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

}