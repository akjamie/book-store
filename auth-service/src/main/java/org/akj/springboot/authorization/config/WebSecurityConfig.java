package org.akj.springboot.authorization.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
	private final AuthenticationProvider userNamePasswordAuthenticationProvider;

	public WebSecurityConfig(@Qualifier("passwordAuthenticationProvider") AuthenticationProvider userNamePasswordAuthenticationProvider) {
		this.userNamePasswordAuthenticationProvider = userNamePasswordAuthenticationProvider;
	}

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
			throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize ->
						authorize.requestMatchers("/oauth2/**",
										"/", "/login",
										"/actuator/**",
										"/actuator")
								.permitAll()
								.anyRequest()
								.authenticated()
				)
				//and().authorizeHttpRequests().antMatchers("/oauth2/clients").authenticated()
				// Form login handles the redirect to the login page from the
				// authorization server filter chain
				.formLogin(form -> form.loginPage("/login").permitAll())
				.authenticationManager(new ProviderManager(List.of(userNamePasswordAuthenticationProvider)));

		return http.build();
	}

}
