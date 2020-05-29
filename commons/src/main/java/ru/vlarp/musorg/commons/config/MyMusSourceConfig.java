package ru.vlarp.musorg.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

@Configuration
public class MyMusSourceConfig {
    @Value("${mymus.db.url}")
    public String myMusUrl;

    @Bean
    @Qualifier("myMusDataSource")
    public DataSource myMusDataSource() {
        org.sqlite.SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl(myMusUrl);
        return sqLiteDataSource;
    }

    @Bean
    @Qualifier("myMusJdbcTemplate")
    public JdbcTemplate myMusJdbcTemplate(@Autowired DataSource myMusDataSource) {
        return new JdbcTemplate(myMusDataSource);
    }
}
