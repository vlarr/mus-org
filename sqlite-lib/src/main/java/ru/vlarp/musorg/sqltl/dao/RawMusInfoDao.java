package ru.vlarp.musorg.sqltl.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RawMusInfoDao {
    private static final String SQL_INSERT_TRACK_INFO = "INSERT INTO R_MUS_INFO(ARTIST, ALBUM, TRACK, SOURCE_ID, DATE_CREATE, TAGS) VALUES (?,?,?,?,?,?)";

    private JdbcTemplate musJdbcTemplate;

    @Autowired
    public void setMusJdbcTemplate(JdbcTemplate musJdbcTemplate) {
        this.musJdbcTemplate = musJdbcTemplate;
    }

    public void save(
            String artist,
            String album,
            String track,
            Long sourceId,
            Long dateCreate,
            String tags

    ) {
        if (dateCreate == null) {
            dateCreate = System.currentTimeMillis();
        }

        musJdbcTemplate.update(SQL_INSERT_TRACK_INFO, artist, album, track, sourceId, dateCreate, tags);
    }
}
