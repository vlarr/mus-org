package ru.vlarp.musorg.flv2.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.flv2.dao.TrackDaoImpl;
import ru.vlarp.musorg.flv2.dto.Track;
import ru.vlarp.musorg.flv2.dto.TrackSource;
import ru.vlarp.musorg.flv2.service.FileService;

import java.util.List;
import java.util.Map;

@Component
public class MergeSqliteFileLogic {
    private static final Logger log = LoggerFactory.getLogger(MergeSqliteFileLogic.class);
    private static final String SQL_SELECT_TRACKS = "SELECT Artist, Title FROM vk_music_1";

    private JdbcTemplate sourceJdbcTemplate;
    private TrackDaoImpl trackDao;
    private FileService fileService;

    @Qualifier("sJdbcTemplate")
    @Autowired
    public void setSourceJdbcTemplate(JdbcTemplate sourceJdbcTemplate) {
        this.sourceJdbcTemplate = sourceJdbcTemplate;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    @Autowired
    public void setTrackDao(TrackDaoImpl trackDao) {
        this.trackDao = trackDao;
    }

    public void process() {
        List<Map<String, Object>> result = sourceJdbcTemplate.queryForList(SQL_SELECT_TRACKS);

        TrackSource trackSource = fileService.convertToTrackSource("D://my_doc//my_music.db");
        log.info("{}", JsonUtils.toJSON(trackSource));

        for (Map<String, Object> stringObjectMap : result) {

            Track track = new Track(
                    String.valueOf(stringObjectMap.getOrDefault("Artist", "")),
                    String.valueOf(stringObjectMap.getOrDefault("Title", "")),
                    2L,
                    trackSource.getLastModifiedTime()
            );

            log.info("{}", JsonUtils.toJSON(track));

            trackDao.insert(track);
        }
    }
}
