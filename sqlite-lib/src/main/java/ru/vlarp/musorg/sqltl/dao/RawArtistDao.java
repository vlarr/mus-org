package ru.vlarp.musorg.sqltl.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.sqltl.dto.RawArtist;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Slf4j
public class RawArtistDao {
    private static final String SQL_INSERT_ARTIST = "INSERT INTO R_ARTISTS(NAME) VALUES (?)";
    private JdbcTemplate musJdbcTemplate;

    @Autowired
    public void setMusJdbcTemplate(JdbcTemplate musJdbcTemplate) {
        this.musJdbcTemplate = musJdbcTemplate;
    }

    public void save(String name) {
        musJdbcTemplate.update(SQL_INSERT_ARTIST, name);
    }

    public RawArtist findByName(String name) {
        return musJdbcTemplate.query(SQL_INSERT_ARTIST, new RowMapper<RawArtist>() {
            @Override
            public RawArtist mapRow(ResultSet rs, int rowNum) throws SQLException {
                RawArtist rawArtist = new RawArtist();
                rawArtist.setId(rs.getLong("ID"));
                rawArtist.setName(rs.getString("NAME"));
                return rawArtist;
            }
        }).stream().findFirst().orElse(null);
    }
}
