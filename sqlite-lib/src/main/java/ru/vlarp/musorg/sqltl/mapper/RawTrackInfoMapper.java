package ru.vlarp.musorg.sqltl.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.vlarp.musorg.commons.utils.ResultSetWrapper;
import ru.vlarp.musorg.sqltl.dto.RawTrackInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RawTrackInfoMapper implements RowMapper<RawTrackInfo> {

    @Override
    public RawTrackInfo mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ResultSetWrapper rs = new ResultSetWrapper(resultSet);
        RawTrackInfo rawTrackInfo = new RawTrackInfo();
        rawTrackInfo.setId(rs.getLong("ID"));
        rawTrackInfo.setArtist(rs.getString("ARTIST"));
        rawTrackInfo.setAlbum(rs.getString("ALBUM"));
        rawTrackInfo.setTrack(rs.getString("TRACK"));
        rawTrackInfo.setSourceId(rs.getLong("SOURCE_ID"));
        rawTrackInfo.setDateCreate(rs.getLong("DATE_CREATE"));
        rawTrackInfo.setState(rs.getLong("STATE"));
        rawTrackInfo.setTags(rs.getString("TAGS"));
        return rawTrackInfo;
    }
}
