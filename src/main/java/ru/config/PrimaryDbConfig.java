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
    @ConfigurationProperties(prefix = "db.oracle")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    NamedParameterJdbcTemplate defaultNamedParameterJdbcTemplate(final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    DataSourceTransactionManager h2Txm(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
