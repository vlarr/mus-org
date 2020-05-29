package ru.vlarp.musorg.flv2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import ru.vlarp.musorg.flv2.config.Config;
import ru.vlarp.musorg.flv2.logic.Logic2;
import ru.vlarp.musorg.flv2.parsers.Parser1Impl;

import java.time.Instant;
import java.util.Arrays;

@Import({Config.class})
@SpringBootApplication
public class App implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(App.class);

    private Logic2 logic2;

    @Autowired
    public void setLogic2(Logic2 logic2) {
        this.logic2 = logic2;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Banner.Mode.OFF);
        ApplicationContext applicationContext = app.run(args);
        SpringApplication.exit(applicationContext);
    }

    @Override
    public void run(String... args) {
        log.info("Hello {}", Arrays.toString(args));
        log.info("now: {}", Instant.now().toEpochMilli());

        //logic2.process("d:\\temp\\music.txt", new Parser1Impl(), 4L);
    }
}
