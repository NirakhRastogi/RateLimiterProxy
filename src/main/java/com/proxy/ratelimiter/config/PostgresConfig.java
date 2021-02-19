package com.proxy.ratelimiter.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {

    @Value("${postgres.host}")
    private String host;

    @Value("${postgres.username}")
    private String username;

    @Value("${postgres.password}")
    private String password;

    @Value("${postgres.port}")
    private Integer port;

    @Value("${postgres.database}")
    private String database;

    @Bean
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(String.format("jdbc:postgresql://%s:%d/%s", host, port, database))
                .username(username)
                .password(password)
                .build();
    }

}
