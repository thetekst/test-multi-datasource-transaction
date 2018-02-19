package ru.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Dmitry Tkachenko on 2/18/18
 */
@Configuration
public class PostgreSqlConfig {

    @Bean
    @Qualifier("postgreSql")
    @ConfigurationProperties(prefix = "db.postgreSql")
    public DataSource postgreSqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier("postgreSqlNamedParameterJdbcTemplate")
    NamedParameterJdbcTemplate postgreSqlNamedParameterJdbcTemplate(@Qualifier("postgreSql") final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Qualifier("postgreSqlTxm")
    DataSourceTransactionManager postgreSqlTxm(@Qualifier("postgreSql") final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
