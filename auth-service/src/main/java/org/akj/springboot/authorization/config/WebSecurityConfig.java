package org.akj.springboot.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    @Qualifier("passwordAuthenticationProvider")
    private AuthenticationProvider userNamePasswordAuthenticationProvider;


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/oauth2/**",  "/actuator/**").permitAll()
                //and().authorizeHttpRequests().antMatchers("/oauth2/clients").authenticated()
                .anyRequest().authenticated().and()
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults())
                .authenticationManager(new ProviderManager(List.of(userNamePasswordAuthenticationProvider)));

        return http.build();
    }

}
