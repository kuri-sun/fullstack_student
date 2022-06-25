package com.fullstack.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("default")
@Configuration
public class DatabaseConfig {

    @Value("${postgres.datasource.url}")
    private String databaseUrl;

    @Value("${postgres.datasource.username}")
    private String databaseUsername;

    @Value("${postgres.datasource.password}")
    private String databasePassword;

    @Profile("default")
    @Bean
    public DataSource getDataSourceProd() {

        return DataSourceBuilder
                .create()
                .url(databaseUrl)
                .username(databaseUsername)
                .password(databasePassword)
                .build();
    }


}