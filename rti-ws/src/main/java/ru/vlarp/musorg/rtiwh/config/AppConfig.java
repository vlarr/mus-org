package ru.vlarp.musorg.rtiwh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vlarp.musorg.rtiwh.logic.DefaultRtiParser;
import ru.vlarp.musorg.rtiwh.logic.RtiParser;

@Configuration
public class AppConfig {
    @Bean
    public RtiParser rtiParser() {
        return new DefaultRtiParser();
    }
}
