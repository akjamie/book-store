package org.akj.springboot.auth.config;

import lombok.Data;
import lombok.ToString;
import org.akj.springboot.auth.vo.KeyPair;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "auth.jwt")
@Data
@ToString
public class JwtTokenConfigProperties {
    private KeyPair keyPair;
    private String rsaKeyPath;
    private long timeToLive;

}
