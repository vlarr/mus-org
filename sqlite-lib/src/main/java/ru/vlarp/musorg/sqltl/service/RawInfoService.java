package ru.vlarp.musorg.sqltl.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.commons.pojo.RawTrackInfo;
import ru.vlarp.musorg.sqltl.dao.RawTrackDao;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;
import ru.vlarp.musorg.sqltl.domain.RawTrackRecord;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    public List<Boolean> consumeTrackInfos(List<RawTrackInfo> rawTrackInfoList) {
        ArrayList<Boolean> results = new ArrayList<>(rawTrackInfoList.size());
        for (RawTrackInfo rawTrackInfo : rawTrackInfoList) {
            log.info("track info: {}", rawTrackInfo);

            if (rawTrackInfo.isAnyEmpty()) {
                results.add(false);
                continue;
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

            results.add(true);
        }

        return results;
    }
}