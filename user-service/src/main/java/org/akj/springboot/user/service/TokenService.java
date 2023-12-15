package org.akj.springboot.user.service;

import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.auth.JwtTokenUtils;
import org.akj.springboot.auth.config.JwtTokenConfigProperties;
import org.akj.springboot.auth.vo.UserToken;
import org.akj.springboot.user.domain.entity.User;
import org.akj.springboot.user.vo.UserDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.akj.springboot.auth.SecurityConstant.TOKEN_AUTHORITIES;
import static org.akj.springboot.auth.SecurityConstant.TOKEN_USER_ID;
import static org.akj.springboot.user.constant.Constant.TOKEN_KEY_PATTERN;

@Service
@Slf4j
public class TokenService {

	private final RedisTemplate<String, String> redisTemplate;

	private final JwtEncoder jwtEncoder;

	private final JwtTokenConfigProperties jwtTokenConfigProperties;

	private final JwtTokenUtils jwtTokenUtils;


	public TokenService(RedisTemplate<String, String> redisTemplate, JwtEncoder jwtEncoder,
						JwtTokenConfigProperties jwtTokenConfigProperties, JwtTokenUtils jwtTokenUtils) {
		this.redisTemplate = redisTemplate;
		this.jwtEncoder = jwtEncoder;
		this.jwtTokenConfigProperties = jwtTokenConfigProperties;
		this.jwtTokenUtils = jwtTokenUtils;
	}

	public UserToken issueToken(UserDetails userDetails) {
		User user = (User) userDetails.getPrincipal();

		// check if token exists in db
		UserToken token = getTokenFromCache(user);

		// if no token could be found, then generate a new token
		if (token == null) {
			token = createNewToken(userDetails);
		}

		return token;
	}

	private UserToken createNewToken(UserDetails userDetails) {
		User user = (User) userDetails.getPrincipal();
		LocalDateTime localDateTime = LocalDateTime.now();
		String tokenId = UUID.randomUUID().toString();
		Instant expiresAt = localDateTime.atZone(ZoneId.systemDefault()).toInstant()
				.plus(jwtTokenConfigProperties.getTimeToLive(), ChronoUnit.SECONDS);
		JwtClaimsSet.Builder claimsSetBuilder = JwtClaimsSet.builder().subject(user.getUserName())
				.id(tokenId)
				.expiresAt(expiresAt)
				.notBefore(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
				.issuedAt(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		claims(userDetails).forEach(claimsSetBuilder::claim);
		JwtClaimsSet jwtClaimsSet = claimsSetBuilder.build();

		Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(SignatureAlgorithm.RS256).build(),
				jwtClaimsSet));

		return jwtTokenUtils.convert(jwt, user.getId());
	}

	private UserToken getTokenFromCache(User user) {
		UserToken userToken = null;
		String cachedToken = (String) redisTemplate.opsForValue().get(String.format(TOKEN_KEY_PATTERN, user.getId()));
		if (StringUtils.isNotEmpty(cachedToken)) {
			try {
				userToken = jwtTokenUtils.convert(cachedToken);
			} catch (JwtException ex) {
				log.info("Invalid or expired token found in redis, will generate new token.");
			}
		}

		return userToken;
	}

	private Map<String, Object> claims(UserDetails userDetails) {
		HashMap<String, Object> claims = new HashMap<>();
		claims.put(TOKEN_USER_ID, userDetails.getPrincipal().getId());

		List<String> groups = userDetails.getAuthorities().stream().map(SimpleGrantedAuthority::getAuthority).toList();
		claims.put(TOKEN_AUTHORITIES, groups);

		return claims;
	}

}
