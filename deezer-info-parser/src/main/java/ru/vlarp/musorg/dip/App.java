package ru.vlarp.musorg.dip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"ru.vlarp.musorg.commons", "ru.vlarp.musorg.dip"})
@PropertySource(value = "${customAppProperties:conf/localhost.properties}", ignoreResourceNotFound = true)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
