package com.chanmul.infrastructure;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DomainDataSourceInitializer {
    @Bean("DataSourceInitializer")
    @Profile("test")
    public DataSourceInitializer DataSourceInitializer(@Qualifier("dataSource") final DataSource dataSource) {
      ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
      ClassPathResource data = new ClassPathResource("data.sql");
      if(data.exists()) {
        resourceDatabasePopulator.addScript(data);
      }
      DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
      dataSourceInitializer.setDataSource(dataSource);
      dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
      return dataSourceInitializer;
    }
}
