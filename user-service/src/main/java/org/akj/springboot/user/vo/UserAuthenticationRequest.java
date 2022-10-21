package org.akj.springboot.user.vo;

import lombok.Data;
import org.akj.springboot.user.domain.AuthType;

import javax.validation.constraints.NotNull;

@Data
public class UserAuthenticationRequest {
    @NotNull
    private AuthType authType;

    private String userName;

    private String password;

    private String code;

    private String phoneNumber;

    private String email;
}
