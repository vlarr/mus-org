package ru.vlarp.musorg.rtic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.vlarp.musorg.commons", "ru.vlarp.musorg.rtic"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}