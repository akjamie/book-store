package org.akj.springboot.common.config;

import jakarta.persistence.EntityManagerFactory;
import org.akj.springboot.common.auditor.JpaDataObjectAuditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@ConditionalOnClass(EntityManagerFactory.class)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

	@Bean
	public AuditorAware<String> auditorAware() {
		return new JpaDataObjectAuditor();
	}
}
