package org.akj.springboot.authorization.security;

import org.akj.springboot.authorization.exception.BadCredentialsException;
import org.akj.springboot.authorization.exception.ErrorCodeMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Qualifier("passwordAuthenticationProvider")
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;

    public UsernamePasswordAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new BadCredentialsException(ErrorCodeMap.BAD_LOGIN_CREDENTIALS.code(), ErrorCodeMap.BAD_LOGIN_CREDENTIALS.message());
        }

        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(user == null || !passwordEncoder.matches(password, user.getPassword())){
            throw new BadCredentialsException(ErrorCodeMap.BAD_LOGIN_CREDENTIALS.code(), ErrorCodeMap.BAD_LOGIN_CREDENTIALS.message());
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
