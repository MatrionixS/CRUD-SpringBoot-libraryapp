package com.rusyn.libreria.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class Data {
    private static final String PASSWORD_KEY="db.password";
    private static final String USERNAME_KEY="db.username";
    private static final String URL_KEY="db.url";

    private final Environment environment;
    @Autowired
    public Data(Environment environment) {
        this.environment = environment;
    }

    @Bean
    private DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(environment.getProperty(URL_KEY));
        dataSource.setUsername(environment.getProperty(USERNAME_KEY));
        dataSource.setPassword(environment.getProperty(PASSWORD_KEY));
        return dataSource;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }
}
