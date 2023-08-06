package com.utp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
  
    @Bean
    @Primary
    @Autowired
    public DataSource dataSource() {
        DataSourceRouting routingDataSource = new DataSourceRouting();
        
        routingDataSource.initDatasource(cibcDataSource(), citiDataSource());
        
        return routingDataSource;
    }
  
    @Bean
    @ConfigurationProperties(prefix = "xtenant.cibc.datasource")
    public DataSource cibcDataSource() {
        return DataSourceBuilder.create().build();
    }
  
    @Bean
    @ConfigurationProperties(prefix = "xtenant.citi.datasource")
    public DataSource citiDataSource() {
        return DataSourceBuilder.create().build();
    }
}