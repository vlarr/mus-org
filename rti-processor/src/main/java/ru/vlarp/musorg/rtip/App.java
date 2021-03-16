package ru.vlarp.musorg.rtip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Slf4j
@SpringBootApplication(scanBasePackages = {
        "ru.vlarp.musorg.commons",
        "ru.vlarp.musorg.sqltl",
        "ru.vlarp.musorg.rtip"
})
public class App implements CommandLineRunner {
    private JdbcTemplate musJdbcTemplate;

    @Autowired
    public void setMusJdbcTemplate(JdbcTemplate musJdbcTemplate) {
        this.musJdbcTemplate = musJdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> records = musJdbcTemplate.query("select * from R_MUS_INFO limit 100",
                (resultSet, i) -> {
                    Long id = resultSet.getLong("ID");
                    String artist = resultSet.getString("ARTIST");
                    String track = resultSet.getString("TRACK");

                    return String.format("%d %s - %s", id, artist, track);
                });
        for (String record : records) {
            log.info(record);
        }
    }
}
