package ru.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Dmitry Tkachenko on 2/18/18
 */
@Configuration
public class PrimaryDbConfig {

    @Bean
    @Primary
//    @Qualifier("h2")
    @ConfigurationProperties(prefix = "db.h2")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    NamedParameterJdbcTemplate defaultNamedParameterJdbcTemplate(/*@Qualifier("h2")*/ final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    DataSourceTransactionManager h2Txm(/*@Qualifier("h2")*/ final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
