package org.akj.springboot.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.akj.springboot.auth.JwtTokenUtils;
import org.akj.springboot.auth.SecurityConstant;
import org.akj.springboot.auth.vo.UserToken;
import org.akj.springboot.common.exception.TechnicalException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.akj.springboot.auth.ErrorCodeMap.INVALID_AUTH_TOKEN;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
	Logger log = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

	private final JwtTokenUtils jwtTokenUtils;

	private final RedisTemplate<Object, Object> redisTemplate;
	private static final String AUTHENTICATION_HEADER_KEY = "Authorization";

	private final ObjectMapper objectMapper;

	public JwtTokenAuthenticationFilter(JwtTokenUtils jwtTokenUtils, RedisTemplate<Object, Object> redisTemplate, ObjectMapper objectMapper) {
		this.jwtTokenUtils = jwtTokenUtils;
		this.redisTemplate = redisTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		log.info("Doing filter for checking auth token.");
		String token = request.getHeader(AUTHENTICATION_HEADER_KEY);
		if (StringUtils.isEmpty(token)) {
			log.debug("No auth token found, skip token checking.");
			filterChain.doFilter(request, response);
			return;
		}

		jwtTokenUtils.checkTokenValidity(token);

		UserToken userToken = jwtTokenUtils.convert(token);

		if (userToken != null && userToken.getClaims().get(SecurityConstant.TOKEN_USER_ID) != null) {
			Map<String, Object> claims = userToken.getClaims();
			Long userId = (Long) claims.get(SecurityConstant.TOKEN_USER_ID);
			List<String> authorities = (List<String>) claims.get(SecurityConstant.TOKEN_AUTHORITIES);

			// check token exists in redis
			UserToken cachedToken = null;
			Object object = redisTemplate.opsForValue().get(String.format(SecurityConstant.TOKEN_KEY_PATTERN, userId));
			if (Objects.nonNull(object)) {
				cachedToken = objectMapper.convertValue(object, UserToken.class);
			}
			// token mismatched
			if (cachedToken != null && !token.equals(cachedToken.getToken())) {
				log.warn("Unrecognized token, ignore.");
				throw new TechnicalException(INVALID_AUTH_TOKEN.code(), INVALID_AUTH_TOKEN.message());
			}

			String userName = userToken.getIssueTo();
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, "",
					authorities.stream().map(SimpleGrantedAuthority::new).toList());
			// set for afterward processing, like logout token cleanup
			authentication.setDetails(userToken.getClaims());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

}