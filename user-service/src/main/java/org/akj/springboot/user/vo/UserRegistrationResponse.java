package org.akj.springboot.user.vo;

import lombok.Data;
import org.akj.springboot.user.domain.AuthType;

@Data
public class UserRegistrationResponse {
    private Long id;

    private String userName;

    private String password;

    private String aliasName;

    private String phoneNumber;

    private String email;

    private AuthType authType;
}
