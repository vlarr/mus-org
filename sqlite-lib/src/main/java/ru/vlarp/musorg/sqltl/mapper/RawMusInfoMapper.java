package ru.vlarp.musorg.sqltl.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.vlarp.musorg.commons.utils.ResultSetWrapper;
import ru.vlarp.musorg.sqltl.dto.RawMusInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RawMusInfoMapper implements RowMapper<RawMusInfo> {

    @Override
    public RawMusInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ResultSetWrapper rs = new ResultSetWrapper(resultSet);
        RawMusInfo rawMusInfo = new RawMusInfo();
        rawMusInfo.setId(rs.getLong("ID"));
        rawMusInfo.setArtist(rs.getString("ARTIST"));
        rawMusInfo.setAlbum(rs.getString("ALBUM"));
        rawMusInfo.setTrack(rs.getString("TRACK"));
        rawMusInfo.setSourceId(rs.getLong("SOURCE_ID"));
        rawMusInfo.setDateCreate(rs.getLong("DATE_CREATE"));
        rawMusInfo.setState(rs.getLong("STATE"));
        rawMusInfo.setTags(rs.getString("TAGS"));
        return rawMusInfo;
    }
}
