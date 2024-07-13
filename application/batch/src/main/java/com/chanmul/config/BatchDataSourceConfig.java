package com.chanmul.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class BatchDataSourceConfig {
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.batch.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
  }
}
