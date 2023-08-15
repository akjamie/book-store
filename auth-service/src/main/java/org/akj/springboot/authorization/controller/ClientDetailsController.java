package org.akj.springboot.authorization.controller;

import org.akj.springboot.authorization.mapper.RegisteredClientMapstructMapper;
import org.akj.springboot.authorization.security.RandomValueStringGenerator;
import org.akj.springboot.authorization.vo.RegisteredClientVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/oauth2/clients")
public class ClientDetailsController {

	private final RegisteredClientRepository registeredClientRepository;

	private final RegisteredClientMapstructMapper registeredClientMapstructMapper;


	public ClientDetailsController(
			RegisteredClientRepository registeredClientRepository,
			RegisteredClientMapstructMapper registeredClientMapstructMapper) {
		this.registeredClientRepository = registeredClientRepository;
		this.registeredClientMapstructMapper = registeredClientMapstructMapper;
	}

	/**
	 * endpoint for client application registration
	 *
	 * @param registeredClientVo of {@link RegisteredClientVo}
	 */
	@PostMapping
	@PreAuthorize("hasAnyAuthority('client:add')")
	@Transactional
	public RegisteredClient register(@RequestBody RegisteredClientVo registeredClientVo) {
		RegisteredClient registeredClient = registeredClientMapstructMapper.registeredClientVoToRegisteredClient(registeredClientVo);

		this.registeredClientRepository.save(registeredClient);

		return registeredClient;
	}


}
