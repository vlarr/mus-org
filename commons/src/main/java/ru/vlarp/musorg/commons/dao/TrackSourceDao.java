package ru.vlarp.musorg.commons.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackSourceDao {
    private static final String SQL_SELECT_TRACK_SOURCE = "SELECT * FROM R_TRACK_SOURCES where NAME = ?";
    private static final String SQL_UPDATE_LAST_MODIFIED_TIME = "UPDATE R_TRACK_SOURCES SET LAST_MODIFIED_TIME = ? WHERE ID = ?";
    private static final String SQL_ACTIVE_SOURCE_NAMES = "SELECT NAME FROM R_TRACK_SOURCES where ACTIVE_CHANNEL = 1";

    private JdbcTemplate myMusJdbcTemplate;

    @Autowired
    @Qualifier("myMusJdbcTemplate")
    public void setMainJbcTemplate(JdbcTemplate myMusJdbcTemplate) {
        this.myMusJdbcTemplate = myMusJdbcTemplate;
    }

    @Cacheable("TrackSourceDao.findIdByName")
    public Long findIdByName(String name) {
        return myMusJdbcTemplate.query(SQL_SELECT_TRACK_SOURCE,
                new Object[]{name},
                resultSet -> {
                    if (resultSet.isClosed()) {
                        return null;
                    }
                    long id = resultSet.getLong("ID");
                    return resultSet.wasNull() ? null : Long.valueOf(id);
                });
    }

    public void updateLastModifiedTime(Long sourceId, Long lastModifiedTime) {
        myMusJdbcTemplate.update(SQL_UPDATE_LAST_MODIFIED_TIME, lastModifiedTime, sourceId);
    }

    public List<String> findActiveSourceNames() {
        return myMusJdbcTemplate.query(SQL_ACTIVE_SOURCE_NAMES, (resultSet, i) -> resultSet.getString("NAME"));
    }
}
