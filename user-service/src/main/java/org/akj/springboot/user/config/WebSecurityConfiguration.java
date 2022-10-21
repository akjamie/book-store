package org.akj.springboot.user.config;

import org.akj.springboot.auth.JwtTokenUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.akj.springboot.user.constant.Constant.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration {
	@Autowired
	private UserService userService;

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

	@Autowired
	JwtAuthenticationExceptionFilter authenticationExceptionFilter;
	@Autowired
	private JwtTokenAuthenticationSuccessHandler jwtTokenAuthenticationSuccessHandler;

	@Autowired
	private JwtTokenAuthenticationFailureHandler jwtTokenAuthenticationFailureHandler;

	@Autowired
	private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.headers().frameOptions().disable()
				//use token thus no need session
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				//access control for endpoints
				.and().authorizeRequests()
				.antMatchers("/h2-console/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator",
						"/actuator/**", "/v1/errors").permitAll()
				.antMatchers(DEFAULT_SIGN_IN_PROCESSING_URL_FORM, DEFAULT_SIGN_IN_PROCESSING_URL_SMS,
						"/v1/auth/**").permitAll()
				.anyRequest().authenticated()
				// login config
				.and().formLogin()
				.loginProcessingUrl(DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
				.failureHandler(jwtTokenAuthenticationFailureHandler)
				.successHandler(jwtTokenAuthenticationSuccessHandler)
				.permitAll()
				//logout
				.and().logout().logoutUrl(DEFAULT_SIGN_OUT_PROCESSING_URL)
				.logoutSuccessHandler(jwtLogoutSuccessHandler)
				// authentication providers
				.and().authenticationManager(authenticationManager())
				// filters
				.addFilterBefore(mobileSmsCodeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(jwtTokenAuthenticationFilter, LogoutFilter.class)
				.addFilterBefore(authenticationExceptionFilter, CorsFilter.class)
				// exception handle
				.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler);

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