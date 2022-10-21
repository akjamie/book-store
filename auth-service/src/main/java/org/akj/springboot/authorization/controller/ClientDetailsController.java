package org.akj.springboot.authorization.controller;

import org.akj.springboot.authorization.security.RandomValueStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/oauth2/clients")
public class ClientDetailsController {

    private final RegisteredClientRepository registeredClientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RandomValueStringGenerator randomValueStringGenerator;
    @Value("${security.oauth2.client.credential-length: 8}")
    private Integer credentialLength;

    public ClientDetailsController(
            RegisteredClientRepository registeredClientRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
            RandomValueStringGenerator randomValueStringGenerator) {
        this.registeredClientRepository = registeredClientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.randomValueStringGenerator = randomValueStringGenerator;
    }

    /**
     * endpoint for client application registration
     *
     * @param clientDetails
     * @return
     */
    @PostMapping
    //@PreAuthorize("hasAnyAuthority('client:add')")
    @Transactional
    public void register(@RequestBody RegisteredClient clientDetails) {

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("$2a$10$ouQW3LaIVobsk87ueGO0Su.yxFGlXzal2fNaNLj5CXtIhegNpPcP.")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("https://www.baidu.com")
                .scope("userinfo.read")
                .scope("userinfo.write")
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

//        String clientId = UUID.randomUUID().toString().replace("-", "");
//        String clientSecret = base64StringGenerator(credentialLength);
//        clientDetails.setClientId(clientId);
//        clientDetails.setClientSecret(bCryptPasswordEncoder.encode(clientSecret));
//        clientDetails.setAuthorizedGrantTypes(List.of("password", "implicit"));
//
//        jdbcClientDetailsService.addClientDetails(clientDetails);
//
//        //reset to plain client secret
//        clientDetails.setClientSecret(clientSecret);

        this.registeredClientRepository.save(registeredClient);
    }

    private String base64StringGenerator(int length) {
        return randomValueStringGenerator.generate(length);
    }
}
