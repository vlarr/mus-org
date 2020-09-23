package ru.vlarp.musorg.sqltl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class MusSourceConfig {
    @Value("${mus.db.url}")
    public String musUrl;

    @Bean
    public DataSource musDataSource() {
        org.sqlite.SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl(musUrl);
        return sqLiteDataSource;
    }

    @Bean
    public JdbcTemplate musJdbcTemplate(@Autowired DataSource musDataSource) {
        return new JdbcTemplate(musDataSource);
    }
}
