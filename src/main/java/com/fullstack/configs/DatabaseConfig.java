package com.fullstack.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("!dev && !it")
@Configuration
public class DatabaseConfig {

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.username}")
    private String databaseUsername;

    @Value("${database.password}")
    private String databasePassword;

    @Profile("!dev && !it")
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