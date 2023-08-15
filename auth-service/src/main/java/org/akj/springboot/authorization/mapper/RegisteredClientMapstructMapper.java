package org.akj.springboot.authorization.mapper;

import org.akj.springboot.authorization.security.RandomValueStringGenerator;
import org.akj.springboot.authorization.vo.RegisteredClientVo;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class RegisteredClientMapstructMapper {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RandomValueStringGenerator randomValueStringGenerator;


	public RegisteredClient registeredClientVoToRegisteredClient(RegisteredClientVo registeredClientVo) {
		return RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId(registeredClientVo.getClientId())
				.clientSecret(bCryptPasswordEncoder.encode(registeredClientVo.getClientSecret()))
				.clientName(registeredClientVo.getClientName())
				.clientSettings(ClientSettings.builder().requireAuthorizationConsent(registeredClientVo.getRequireAuthorizationConsent()).build())
				.redirectUri(registeredClientVo.getRedirectUri())
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.scopes(scopes -> scopes.addAll(registeredClientVo.getScopes()))
				.authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(registeredClientVo.getAuthorizationGrantTypes()))
				.build();
	}

}
