package org.akj.springboot.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.akj.springboot.user.domain.AuthType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationResponse {
    private String token;
    private AuthType type;
}
