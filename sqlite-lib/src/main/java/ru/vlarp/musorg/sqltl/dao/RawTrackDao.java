package ru.vlarp.musorg.sqltl.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.sqltl.domain.RawTrackRecord;
import ru.vlarp.musorg.sqltl.mapper.RawTrackRecordMapper;

import java.util.List;

@Component
@Slf4j
public class RawTrackDao {
    private static final String SQL_INSERT_TRACK_INFO = "INSERT INTO R_TRACKS(ARTIST, ALBUM, TITLE, TRACK_SOURCE_ID, CREATION_TIME, STATE) VALUES (?,?,?,?,?,?)";
    private static final String SQL_SELECT_LAST_TRACKS = "SELECT * FROM R_TRACKS ORDER BY CREATION_TIME DESC LIMIT ?";
    private static final String SQL_SELECT_FIRST_TRACKS_WITH_STATE = "SELECT * FROM R_TRACKS WHERE STATE IS NULL ORDER BY ID LIMIT ?";

    private JdbcTemplate mainJbcTemplate;

    @Autowired
    @Qualifier("myMusJdbcTemplate")
    public void setMainJbcTemplate(JdbcTemplate mainJdbcTemplate) {
        this.mainJbcTemplate = mainJdbcTemplate;
    }

    public void save(RawTrackRecord rawTrack) {
        log.info("save: {}", rawTrack);

        if (rawTrack == null) {
            return;
        }

        if (rawTrack.getCreationTime() == null) {
            rawTrack.setCreationTime(System.currentTimeMillis());
        }

        mainJbcTemplate.update(SQL_INSERT_TRACK_INFO, rawTrack.getArtist(), rawTrack.getAlbum(), rawTrack.getTitle(), rawTrack.getTrackSourceId(), rawTrack.getCreationTime(), rawTrack.getState());
    }

    public List<RawTrackRecord> findLast(Integer limit) {
        return mainJbcTemplate.query(SQL_SELECT_LAST_TRACKS, new Object[]{limit}, new RawTrackRecordMapper());
    }

    public List<RawTrackRecord> findFirstWithNullState(Integer limit) {
        return mainJbcTemplate.query(SQL_SELECT_FIRST_TRACKS_WITH_STATE, new Object[]{limit}, new RawTrackRecordMapper());
    }
}
