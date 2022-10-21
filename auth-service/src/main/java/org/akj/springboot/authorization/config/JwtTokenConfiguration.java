package org.akj.springboot.authorization.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.security.KeyStore;

@Configuration
public class JwtTokenConfiguration {

	@Value("${security.oauth2.jwt.key-pair.store-pass}")
	private String keyStorePass;

	@Value("${security.oauth2.jwt.key-pair.alias}")
	private String keyPairAlias;

	@Value("${security.oauth2.jwt.key-pair.file-path}")
	private String keyStoreFilePath;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@SneakyThrows
	@Bean
	public JWK jwk(){
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		Resource keystoreFileResource = null;
		if(keyStoreFilePath.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
			keystoreFileResource = new FileUrlResource(keyStoreFilePath);
		}else {
			keystoreFileResource = new ClassPathResource(keyStoreFilePath);
		}

		Assert.notNull(keystoreFileResource, "Invalid key store path.");

		keyStore.load(keystoreFileResource.getInputStream(), keyStorePass.toCharArray());

		JWK jwk = JWK.load(keyStore, keyPairAlias, keyStorePass.toCharArray());
		Assert.notNull(jwk, "Load RSA key failed.");
		return jwk;
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		JWKSet jwkSet = new JWKSet(jwk());
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}
}