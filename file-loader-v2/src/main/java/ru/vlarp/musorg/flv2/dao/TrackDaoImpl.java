package ru.vlarp.musorg.flv2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.flv2.dto.Track;

@Component
public class TrackDaoImpl implements TrackDao {
    private static final String INSERT_TRACK = "INSERT INTO R_TRACKS_STG(ARTIST, TITLE, TRACK_SOURCE_ID, CREATION_TIME) VALUES (?,?,?,?)";

    private JdbcTemplate tJdbcTemplate;

    @Qualifier("tJdbcTemplate")
    @Autowired
    public void setJdbcTemplate(JdbcTemplate tJdbcTemplate) {
        this.tJdbcTemplate = tJdbcTemplate;
    }

    public void insert(Track track) {
        tJdbcTemplate.update(INSERT_TRACK, track.getArtist(), track.getTitle(), track.getTrackSource(), track.getCreateTime());
    }
}
