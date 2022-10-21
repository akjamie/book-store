package org.akj.springboot.user.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
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
import org.akj.springboot.auth.config.JwtTokenConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

@Configuration
@Slf4j
public class JwtTokenConfiguration {

	@Autowired
	private JwtTokenConfigProperties jwtTokenConfigProperties;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtTokenUtils jwtTokenUtils() {
		return new JwtTokenUtils(jwtEncoder(), jwtDecoder(), jwsVerifier());
	}

//	@SneakyThrows
//	//@Bean
//	public JWK jwk() {
//		log.info("properties: {}", jwtTokenConfigProperties);
//		KeyStore keyStore = KeyStore.getInstance("PKCS12");
//		Resource keystoreFileResource = null;
//
//		KeyPair keyPair = jwtTokenConfigProperties.getKeyPair();
//		String storePath = keyPair.getStoreFilePath();
//		String storePass = keyPair.getStorePass();
//		String kid = keyPair.getKid();
//
//		if (storePath.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
//			log.debug("trying to load keystore from file, path:{}", storePath);
//			String path = storePath.substring(storePath.indexOf(ResourceUtils.FILE_URL_PREFIX) + ResourceUtils.FILE_URL_PREFIX.length());
//			keystoreFileResource = new FileSystemResource(path);
//		} else {
//			keystoreFileResource = new ClassPathResource(storePath);
//		}
//
//		Assert.notNull(keystoreFileResource, "Invalid key store path.");
//
//		keyStore.load(keystoreFileResource.getInputStream(), storePass.toCharArray());
//		JWK jwk = JWK.load(keyStore, kid, storePass.toCharArray());
//		Assert.notNull(jwk, "Load RSA key pair failed.");
//
//		return jwk;
//	}

	@SneakyThrows
	@Bean
	public JWK jwk() {
		String privKeyPath = jwtTokenConfigProperties.getRsaKeyPath();
		String keyString;
		if (privKeyPath.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
			log.debug("trying to load RSA key from file, path:{}", privKeyPath);
			String path = privKeyPath.substring(privKeyPath.indexOf(ResourceUtils.FILE_URL_PREFIX) + ResourceUtils.FILE_URL_PREFIX.length());
			keyString = IOUtils.readInputStreamToString(new FileSystemResource(path).getInputStream());
		} else {
			keyString = IOUtils.readInputStreamToString(new ClassPathResource(privKeyPath).getInputStream());
		}
		Assert.notNull(keyString, "Cannot load the RSA key.");

		return JWK.parseFromPEMEncodedObjects(keyString);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		JWKSet jwkSet = new JWKSet(jwk());
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	@SneakyThrows
	@Bean
	public RSASSASigner rsassaSigner() {
		return new RSASSASigner(jwk().toRSAKey());
	}

	@SneakyThrows
	@Bean
	public JWSVerifier jwsVerifier() {
		return new RSASSAVerifier(jwk().toPublicJWK().toRSAKey());
	}

	@Bean
	public JwtEncoder jwtEncoder() {
		JwtEncoder encoder = new NimbusJwtEncoder(jwkSource());

		return encoder;
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		DefaultJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
		jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<SecurityContext>(
				JWSAlgorithm.RS256, jwkSource()));

		return new NimbusJwtDecoder(jwtProcessor);
	}

}