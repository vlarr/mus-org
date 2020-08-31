package ru.vlarp.musorg.dip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "ru.vlarp.musorg.commons",
        "ru.vlarp.musorg.sqltl",
        "ru.vlarp.musorg.dip"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
