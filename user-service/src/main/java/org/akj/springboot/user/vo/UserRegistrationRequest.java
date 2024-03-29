package org.akj.springboot.user.vo;

import lombok.Data;
import org.akj.springboot.user.domain.AuthType;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

@Data
public class UserRegistrationRequest {
    @NotNull
    @Length(min = 6, max = 32)
    private String userName;
    @NotNull
    @Length(min = 8, max = 32)
    private String password;

    private String aliasName;

    private String phoneNumber;

    private String email;

    private AuthType authType = AuthType.PASSWORD;
}
