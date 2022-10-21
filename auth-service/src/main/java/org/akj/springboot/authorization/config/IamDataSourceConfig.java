package org.akj.springboot.authorization.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "org.akj.springboot.authorization.repository", entityManagerFactoryRef =
        "iamEntityManagerFactory", transactionManagerRef = "iamTransactionManager")
public class IamDataSourceConfig {

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        // show sql
        hibernateJpaVendorAdapter.setShowSql(true);
        //auto ddl
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        //database type
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");

        return hibernateJpaVendorAdapter;
    }

    protected Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", CamelCaseToUnderscoresNamingStrategy.class.getName());
        return props;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.iam")
    public DataSourceProperties iamDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource iamDataSource() {
        return iamDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "iamEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean iamEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = builder
                .dataSource(iamDataSource())
                .packages("org.akj.springboot.authorization.domain.iam")
                .build();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaProperties());

        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager iamTransactionManager(
            final @Qualifier("iamEntityManagerFactory") LocalContainerEntityManagerFactoryBean iamEntityManagerFactory) {
        return new JpaTransactionManager(iamEntityManagerFactory.getObject());
    }
}
