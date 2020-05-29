package ru.vlarp.musorg.commons.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.commons.pojo.RawPlaylistTrackInfo;

import java.util.List;

@Service
public class RawPlaylistTrackInfoDao {
    private static final Logger log = LoggerFactory.getLogger(RawPlaylistTrackInfoDao.class);

    private static final String SQL_INSERT_TRACK_INFO = "INSERT INTO R_TRACKS_STG(ARTIST, TITLE, TRACK_SOURCE_ID, CREATION_TIME) VALUES (?,?,?,?)";
    private static final String SQL_SELECT_LAST_TRACKS = "SELECT * FROM R_TRACKS_STG ORDER BY CREATION_TIME DESC LIMIT ?";

    private JdbcTemplate mainJbcTemplate;

    @Autowired
    @Qualifier("myMusJdbcTemplate")
    public void setMainJbcTemplate(JdbcTemplate mainJdbcTemplate) {
        this.mainJbcTemplate = mainJdbcTemplate;
    }

    public void saveToStaging(String artist, String title, Long sourceId, Long timeStamp) {
        log.info("saveToStaging: {}, {}, {}, {}", artist, title, sourceId, timeStamp);
        mainJbcTemplate.update(SQL_INSERT_TRACK_INFO, artist, title, sourceId, timeStamp);
    }

    public void saveToStaging(RawPlaylistTrackInfo trackInfo) {
        log.info("saveToStaging: {}, {}, {}, {}", trackInfo.getArtist(), trackInfo.getTitle(), trackInfo.getTrackSourceId(), trackInfo.getCreationTime());
        mainJbcTemplate.update(SQL_INSERT_TRACK_INFO, trackInfo.getArtist(), trackInfo.getTitle(), trackInfo.getTrackSourceId(), trackInfo.getCreationTime());
    }

    public List<RawPlaylistTrackInfo> findLastByLimit(Integer trackLimit) {
        log.info("findLastByLimit: {}", trackLimit);

        return mainJbcTemplate.query(SQL_SELECT_LAST_TRACKS, new Object[]{trackLimit}, (resultSet, i) -> {
            RawPlaylistTrackInfo result = new RawPlaylistTrackInfo();
            result.setArtist(resultSet.getString("ARTIST"));
            result.setTitle(resultSet.getString("TITLE"));
            result.setTrackSourceId(resultSet.getLong("TRACK_SOURCE_ID"));
            result.setCreationTime(resultSet.getLong("CREATION_TIME"));
            return result;
        });
    }
}
