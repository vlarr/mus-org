package ru.vlarp.musorg.rtip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = {
        "ru.vlarp.musorg.commons",
        "ru.vlarp.musorg.sqltl",
        "ru.vlarp.musorg.rtip"
}, exclude = {JdbcTemplateAutoConfiguration.class})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
