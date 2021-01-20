package ru.vlarp.musorg.sqltl.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.sqltl.dto.RawMusInfo;
import ru.vlarp.musorg.sqltl.mapper.RawMusInfoMapper;

import java.util.List;

@Component
@Slf4j
public class RawMusInfoDao {
    private static final String SQL_INSERT_TRACK_INFO = "INSERT INTO R_MUS_INFO(ARTIST, ALBUM, TRACK, SOURCE_ID, DATE_CREATE, TAGS) VALUES (?,?,?,?,?,?)";
    private static final String SQL_SELECT_WITH_NULL_STATE = "SELECT * FROM R_MUS_INFO WHERE STATE IS NULL LIMIT ?";

    private JdbcTemplate musJdbcTemplate;

    @Autowired
    public void setMusJdbcTemplate(JdbcTemplate musJdbcTemplate) {
        this.musJdbcTemplate = musJdbcTemplate;
    }

    public void save(String artist, String album, String track, Long sourceId, Long dateCreate, String tags) {
        if (dateCreate == null) {
            dateCreate = System.currentTimeMillis();
        }

        musJdbcTemplate.update(SQL_INSERT_TRACK_INFO, artist, album, track, sourceId, dateCreate, tags);
    }

    public List<RawMusInfo> findWithNullState(Long limit) {
        return musJdbcTemplate.query(SQL_SELECT_WITH_NULL_STATE, new Object[]{limit}, new RawMusInfoMapper());
    }
}
