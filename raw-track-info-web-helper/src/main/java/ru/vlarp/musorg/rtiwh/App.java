package ru.vlarp.musorg.rtiwh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "ru.vlarp.musorg.rtiwh",
        "ru.vlarp.musorg.rlib",
        "ru.vlarp.musorg.rmql"
})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
