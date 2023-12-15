package org.akj.springboot.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {
    String id;
    String token;
    String issueTo;
    Long uid;
    Instant expiresAt;

    @JsonIgnore
    Map<String,Object> claims = new HashMap<>();
}
