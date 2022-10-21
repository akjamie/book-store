package org.akj.springboot.user;

import org.akj.springboot.auth.config.GenericSecurityConfig;
import org.akj.springboot.auth.config.JwtTokenConfigProperties;
import org.akj.springboot.auth.config.RedisConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = {"org.akj.springboot.user", "org.akj.springboot.common"},
		exclude = {RedisRepositoriesAutoConfiguration.class,})
@Import({JwtTokenConfigProperties.class, RedisConfig.class, GenericSecurityConfig.class})
public class UserServiceApplication {
	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
