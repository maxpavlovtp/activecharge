package com.km220.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DatabaseConfiguration {

  @Bean
  public NamedParameterJdbcTemplate namedParameterJdbcTemplate(HikariDataSource hikariDataSource){
    return new NamedParameterJdbcTemplate(hikariDataSource);
  }
}
