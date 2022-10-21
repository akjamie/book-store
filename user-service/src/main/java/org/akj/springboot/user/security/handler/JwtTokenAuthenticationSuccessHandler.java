package org.akj.springboot.user.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.auth.config.JwtTokenConfigProperties;
import org.akj.springboot.auth.vo.UserToken;
import org.akj.springboot.user.domain.entity.User;
import org.akj.springboot.user.service.TokenService;
import org.akj.springboot.user.vo.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

import static org.akj.springboot.user.constant.Constant.TOKEN_KEY_PATTERN;

@Component
@Slf4j
public class JwtTokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    TokenService tokenService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenConfigProperties jwtTokenConfigProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        try {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            UserDetails user = (UserDetails) authentication.getPrincipal();
            User u = user.getPrincipal();

            UserToken token = tokenService.issueToken(user);
            // cache in redis
            redisTemplate.opsForValue().set(String.format(TOKEN_KEY_PATTERN, u.getId()), token.getToken(),
                    Duration.ofSeconds(jwtTokenConfigProperties.getTimeToLive()));

            httpServletResponse.getWriter().write(mapper.writeValueAsString(token));
            httpServletResponse.getWriter().close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

