package ru.vlarp.musorg.commons.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.commons.pojo.RawTrack;

import java.util.List;

@Service
public class RawTrackDao {
    private static final Logger log = LoggerFactory.getLogger(RawTrackDao.class);
    private static final String SQL_INSERT_TRACK_INFO = "INSERT INTO R_TRACKS(ARTIST, ALBUM, TITLE, TRACK_SOURCE_ID, CREATION_TIME) VALUES (?,?,?,?,?)";
    private static final String SQL_SELECT_LAST_TRACKS = "SELECT * FROM R_TRACKS ORDER BY CREATION_TIME DESC LIMIT ?";

    private JdbcTemplate mainJbcTemplate;

    @Autowired
    @Qualifier("myMusJdbcTemplate")
    public void setMainJbcTemplate(JdbcTemplate mainJdbcTemplate) {
        this.mainJbcTemplate = mainJdbcTemplate;
    }

    public void save(RawTrack rawTrack) {
        log.info("save: {}", rawTrack);

        if (rawTrack == null) {
            return;
        }

        if (rawTrack.getCreationTime() == null) {
            rawTrack.setCreationTime(System.currentTimeMillis());
        }

        mainJbcTemplate.update(SQL_INSERT_TRACK_INFO, rawTrack.getArtist(), rawTrack.getAlbum(), rawTrack.getTitle(), rawTrack.getTrackSourceId(), rawTrack.getCreationTime());
    }

    public List<RawTrack> findLast(Integer limit) {
        return mainJbcTemplate.query(SQL_SELECT_LAST_TRACKS, new Object[]{limit}, (resultSet, i) -> {
            RawTrack result = new RawTrack();
            result.setArtist(resultSet.getString("ARTIST"));
            result.setAlbum(resultSet.getString("ALBUM"));
            result.setTitle(resultSet.getString("TITLE"));
            result.setTrackSourceId(resultSet.getLong("TRACK_SOURCE_ID"));
            result.setCreationTime(resultSet.getLong("CREATION_TIME"));
            return result;
        });
    }
}
