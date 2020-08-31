package ru.vlarp.musorg.sqltl.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.vlarp.musorg.sqltl.domain.RawTrackRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RawTrackRecordMapper implements RowMapper<RawTrackRecord> {
    @Override
    public RawTrackRecord mapRow(ResultSet resultSet, int i) throws SQLException {
        RawTrackRecord result = new RawTrackRecord();
        result.setId(resultSet.getLong("ID"));
        result.setArtist(resultSet.getString("ARTIST"));
        result.setAlbum(resultSet.getString("ALBUM"));
        result.setTitle(resultSet.getString("TITLE"));
        result.setTrackSourceId(resultSet.getLong("TRACK_SOURCE_ID"));
        result.setCreationTime(resultSet.getLong("CREATION_TIME"));
        result.setState(resultSet.getLong("STATE"));
        return result;
    }
}