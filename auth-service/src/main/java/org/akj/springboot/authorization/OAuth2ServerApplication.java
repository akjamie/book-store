package org.akj.springboot.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Springboot application - Entry point
 *
 * @author Jamie Y L Zhang
 */

@SpringBootApplication(scanBasePackages = {"org.akj.springboot.common", "org.akj.springboot.authorization"},
        exclude = {DataSourceAutoConfiguration.class})
//@EnableDiscoveryClient
public class OAuth2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2ServerApplication.class, args);
    }

}
