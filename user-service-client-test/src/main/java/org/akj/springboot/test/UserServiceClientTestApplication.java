package org.akj.springboot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"org.akj.springboot.test", "org.akj.springboot.common",
		"org.akj.springboot.auth"},
		exclude = {RedisRepositoriesAutoConfiguration.class})
@EnableFeignClients(basePackages = "org.akj.springboot.user")
public class UserServiceClientTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceClientTestApplication.class);
	}
}