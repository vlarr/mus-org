package ru.vlarp.musorg.rmip.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.rmip.dto.RawMusInfoRecord;
import ru.vlarp.musorg.sqltl.dao.RawMusInfoDao;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CollectorLogic {
    private JdbcTemplate musJdbcTemplate;
    private RawMusInfoDao rawMusInfoDao;

    @Autowired
    public void setMusJdbcTemplate(JdbcTemplate musJdbcTemplate) {
        this.musJdbcTemplate = musJdbcTemplate;
    }

    @Autowired
    public void setRawMusInfoDao(RawMusInfoDao rawMusInfoDao) {
        this.rawMusInfoDao = rawMusInfoDao;
    }

    private List<RawMusInfoRecord> readTrackInfoList() {
        return musJdbcTemplate.query("SELECT * FROM R_TRACKS", new RowMapper<RawMusInfoRecord>() {
            @Override
            public RawMusInfoRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                RawMusInfoRecord rawMusInfoRecord = new RawMusInfoRecord();

                rawMusInfoRecord.setId(rs.getLong("ID"));
                rawMusInfoRecord.setArtist(rs.getString("ARTIST"));
                rawMusInfoRecord.setAlbum(rs.getString("ALBUM"));
                rawMusInfoRecord.setTrack(rs.getString("TITLE"));

                Long trackSourceId = rs.getLong("TRACK_SOURCE_ID");
                rawMusInfoRecord.setSourceId(rs.wasNull() ? null : trackSourceId);

                Long dateCreate = rs.getLong("CREATION_TIME");
                rawMusInfoRecord.setDateCreate(rs.wasNull() ? null : dateCreate);

                rawMusInfoRecord.setTags(rs.getString("TAGS"));

                return rawMusInfoRecord;
            }
        });
    }

    private List<RawMusInfoRecord> readAlbumInfoList() {
        return musJdbcTemplate.query("SELECT * FROM R_ALBUMS", new RowMapper<RawMusInfoRecord>() {
            @Override
            public RawMusInfoRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                RawMusInfoRecord rawMusInfoRecord = new RawMusInfoRecord();

                rawMusInfoRecord.setId(rs.getLong("ID"));
                rawMusInfoRecord.setArtist(rs.getString("ARTIST"));
                rawMusInfoRecord.setAlbum(rs.getString("TITLE"));

                Long trackSourceId = rs.getLong("TRACK_SOURCE_ID");
                rawMusInfoRecord.setSourceId(rs.wasNull() ? null : trackSourceId);

                Long dateCreate = rs.getLong("CREATION_TIME");
                rawMusInfoRecord.setDateCreate(rs.wasNull() ? null : dateCreate);

                return rawMusInfoRecord;
            }
        });
    }

    private List<RawMusInfoRecord> readArtistInfoList() {
        return musJdbcTemplate.query("SELECT * FROM R_ARTISTS", new RowMapper<RawMusInfoRecord>() {
            @Override
            public RawMusInfoRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
                RawMusInfoRecord rawMusInfoRecord = new RawMusInfoRecord();

                rawMusInfoRecord.setId(rs.getLong("ID"));
                rawMusInfoRecord.setArtist(rs.getString("NAME"));

                Long trackSourceId = rs.getLong("TRACK_SOURCE_ID");
                rawMusInfoRecord.setSourceId(rs.wasNull() ? null : trackSourceId);

                Long dateCreate = rs.getLong("CREATION_TIME");
                rawMusInfoRecord.setDateCreate(rs.wasNull() ? null : dateCreate);

                return rawMusInfoRecord;
            }
        });
    }

    @PostConstruct
    public void init() {
        List<RawMusInfoRecord> records = new ArrayList<>();

        records.addAll(readArtistInfoList());
        records.addAll(readAlbumInfoList());
        records.addAll(readTrackInfoList());

        records = records.stream()
                .sorted(Comparator.comparing(RawMusInfoRecord::getDateCreate))
                .collect(Collectors.toList());

        for (RawMusInfoRecord rawMusInfoRecord : records) {
            log.info("insert {}", rawMusInfoRecord);
            rawMusInfoDao.save(
                    rawMusInfoRecord.getArtist(),
                    rawMusInfoRecord.getAlbum(),
                    rawMusInfoRecord.getTrack(),
                    rawMusInfoRecord.getSourceId(),
                    rawMusInfoRecord.getDateCreate(),
                    rawMusInfoRecord.getTags()
            );
        }
    }
}
