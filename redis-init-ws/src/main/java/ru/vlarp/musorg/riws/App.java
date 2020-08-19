package ru.vlarp.musorg.riws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication(scanBasePackages = {
        "ru.vlarp.musorg.commons",
        "ru.vlarp.musorg.rlib",
        "ru.vlarp.musorg.riws"
})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
