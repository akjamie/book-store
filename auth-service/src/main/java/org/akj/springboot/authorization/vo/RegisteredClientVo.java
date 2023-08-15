package org.akj.springboot.authorization.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.List;

@Data
public class RegisteredClientVo {

	@NotNull
	@Length(min = 4)
	private String clientId;

	@NotNull
	@Length(min = 6, max = 32)
	private String clientSecret;

	private String clientName;

	@NotNull
	private String redirectUri;

	@NotNull
	@Size(min = 1)
	private List<AuthorizationGrantType> authorizationGrantTypes;

	@Size(min = 1)
	private List<String> scopes;

	private Boolean requireAuthorizationConsent;
}
