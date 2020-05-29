package ru.vlarp.musorg.flv2.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.flv2.dao.TrackDaoImpl;
import ru.vlarp.musorg.flv2.dto.Track;
import ru.vlarp.musorg.flv2.dto.TrackSource;
import ru.vlarp.musorg.flv2.parsers.ParserBase;
import ru.vlarp.musorg.flv2.service.FileService;

import java.util.List;

@Component
public class Logic2 {
    private static final Logger log = LoggerFactory.getLogger(Logic2.class);

    private TrackDaoImpl trackDao;
    private FileService fileService;

    @Autowired
    public void setTrackDao(TrackDaoImpl trackDao) {
        this.trackDao = trackDao;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public void process(String fileName, ParserBase parser, Long trackSourceId) {
        TrackSource trackSource = fileService.convertToTrackSource(fileName);

        log.info("{}", JsonUtils.toJSON(trackSource));

        List<Track> tracks = parser.readFile(fileName);
        for (Track track : tracks) {
            track.setTrackSource(trackSourceId);
            track.setCreateTime(trackSource.getLastModifiedTime());
            log.info("{}", JsonUtils.toJSON(track));
            trackDao.insert(track);
        }
    }
}
