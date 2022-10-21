package org.akj.springboot.user.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.auth.SecurityConstant;
import org.akj.springboot.common.exception.TechnicalException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static org.akj.springboot.user.constant.Constant.TOKEN_KEY_PATTERN;
import static org.akj.springboot.user.exception.ErrorCodeMap.ILLEGAL_TOKEN_CONTENT;
import static org.akj.springboot.user.exception.ErrorCodeMap.UNAUTHENTICATED;

@Component
@Slf4j
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
	private final RedisTemplate redisTemplate;

	public JwtLogoutSuccessHandler(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		// delete token from redis
		Long userId = extractUserId(authentication);
		redisTemplate.delete(String.format(TOKEN_KEY_PATTERN, userId));

		response.getWriter().write("Logout successfully.");
		response.getWriter().close();
	}

	private static Long extractUserId(Authentication authentication) {
		if (null == authentication) {
			log.error("Not authenticated");
			throw new TechnicalException(UNAUTHENTICATED.code(), UNAUTHENTICATED.message());
		}

		if (null == authentication.getDetails() ||
				!((Map<String, Object>) authentication.getDetails()).containsKey(SecurityConstant.TOKEN_USER_ID)) {
			log.warn("No userid could be found.");
			throw new TechnicalException(ILLEGAL_TOKEN_CONTENT.code(), ILLEGAL_TOKEN_CONTENT.message());
		}

		Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
		Long userId = (Long) details.get(SecurityConstant.TOKEN_USER_ID);
		return userId;
	}
}
