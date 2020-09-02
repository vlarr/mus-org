package ru.vlarp.musorg.rtic.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.commons.pojo.RawTrackInfo;
import ru.vlarp.musorg.sqltl.dao.RawTrackDao;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;
import ru.vlarp.musorg.sqltl.domain.RawTrackRecord;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class RawInfoService {
    private TrackSourceDao trackSourceDao;

    private RawTrackDao rawTrackDao;

    @Autowired
    public void setTrackSourceDao(TrackSourceDao trackSourceDao) {
        this.trackSourceDao = trackSourceDao;
    }

    @Autowired
    public void setRawTrackDao(RawTrackDao rawTrackDao) {
        this.rawTrackDao = rawTrackDao;
    }

    public void processRawTrackInfo(RawTrackInfo rawTrackInfo) {
        log.info("track info: {}", rawTrackInfo);

        if (rawTrackInfo.isAnyEmpty()) {
            //todo
            return;
        }

        Map<String, Long> sources = Stream.of(StringUtils.split(rawTrackInfo.getSources(), ','))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toMap(
                        Function.identity(), source -> trackSourceDao.findIdByName(source)
                ));

        Long timeStamp = Instant.now().toEpochMilli();

        for (Map.Entry<String, Long> entry : sources.entrySet()) {
            if (entry.getValue() != null) {

                RawTrackRecord rawPlaylistTrackInfo = RawTrackRecord
                        .builder()
                        .artist(rawTrackInfo.getArtist())
                        .title(rawTrackInfo.getTitle())
                        .album(rawTrackInfo.getAlbum())
                        .trackSourceId(entry.getValue())
                        .creationTime(timeStamp)
                        .build();

                rawTrackDao.save(rawPlaylistTrackInfo);
                trackSourceDao.updateLastModifiedTime(entry.getValue(), timeStamp);
            } else {
                log.warn("not find source: {}", entry.getKey());
            }
        }
    }
}
