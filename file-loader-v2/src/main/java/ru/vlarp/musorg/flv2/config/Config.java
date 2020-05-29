package ru.vlarp.musorg.flv2.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Config {

    @Qualifier("tJdbcTemplate")
    @Bean
    public JdbcTemplate jdbcTemplate() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(org.sqlite.SQLiteDataSource.class);
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:D:/my_doc/myMus.db");
        return new JdbcTemplate(dataSourceBuilder.build());
    }

    @Qualifier("sJdbcTemplate")
    @Bean
    public JdbcTemplate sourceJdbcTemplate() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.type(org.sqlite.SQLiteDataSource.class);
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:D:/my_doc/my_music.db");
        return new JdbcTemplate(dataSourceBuilder.build());
    }
}
