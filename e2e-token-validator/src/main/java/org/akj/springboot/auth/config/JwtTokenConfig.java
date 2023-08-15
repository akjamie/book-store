package org.akj.springboot.auth.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.IOUtils;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.akj.springboot.auth.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

@Configuration
@Slf4j
public class JwtTokenConfig {

	@Autowired
	private JwtTokenConfigProperties jwtTokenConfigProperties;

	@Bean
	@ConditionalOnMissingBean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtTokenUtils jwtTokenUtils() {
		return new JwtTokenUtils(null, jwtDecoder(), jwsVerifier());
	}


	@SneakyThrows
	@ConditionalOnMissingBean
	@Bean
	public JWK jwk() {
		String rsaKeyPath = jwtTokenConfigProperties.getRsaKeyPath();
		String keyString;
		if (rsaKeyPath.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
			log.debug("trying to load RSA key from file, path:{}", rsaKeyPath);
			String path = rsaKeyPath.substring(rsaKeyPath.indexOf(ResourceUtils.FILE_URL_PREFIX) + ResourceUtils.FILE_URL_PREFIX.length());
			keyString = IOUtils.readInputStreamToString(new FileSystemResource(path).getInputStream());
		} else {
			keyString = IOUtils.readInputStreamToString(new ClassPathResource(rsaKeyPath).getInputStream());
		}
		Assert.notNull(keyString, "Cannot load the RSA key.");

		return JWK.parseFromPEMEncodedObjects(keyString);
	}

	@Bean
	@ConditionalOnMissingBean
	public JWKSource<SecurityContext> jwkSource() {
		JWKSet jwkSet = new JWKSet(jwk());
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	@SneakyThrows
	@Bean
	@ConditionalOnMissingBean
	public JWSVerifier jwsVerifier() {
		return new RSASSAVerifier(jwk().toPublicJWK().toRSAKey());
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtDecoder jwtDecoder() {
		DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
		jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<SecurityContext>(
				JWSAlgorithm.RS256, jwkSource()));

		return new NimbusJwtDecoder(jwtProcessor);
	}

}