package ru.vlarp.musorg.ricw.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.commons.dao.RawPlaylistTrackInfoDao;
import ru.vlarp.musorg.commons.dao.TrackSourceDao;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;
import ru.vlarp.musorg.commons.pojo.RawPlaylistTrackInfo;
import ru.vlarp.musorg.commons.pojo.TrackInfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RawInfoService {
    private static final Logger log = LoggerFactory.getLogger(RawInfoService.class);

    private TrackSourceDao trackSourceDao;
    private RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao;

    @Autowired
    public void setTrackSourceDao(TrackSourceDao trackSourceDao) {
        this.trackSourceDao = trackSourceDao;
    }

    @Autowired
    public void setRawTrackInfoDao(RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao) {
        this.rawPlaylistTrackInfoDao = rawPlaylistTrackInfoDao;
    }

    public ParseTrackInfoResult tryParseTrackInfo(String rawTrackInfo) {
        log.info(rawTrackInfo);

        String[] splitStr = StringUtils.split(rawTrackInfo, "-—");

        if (splitStr.length == 2) {
            splitStr[0] = StringUtils.trim(splitStr[0]);
            splitStr[1] = StringUtils.trim(splitStr[1]);

            ParseTrackInfoResult result = new ParseTrackInfoResult(splitStr[0], splitStr[1], "OK");
            log.info("parsed:{}", result);
            return result;
        }

        return new ParseTrackInfoResult(null, null, "ERROR");
    }

    public List<Boolean> consumeTrackInfos(List<TrackInfo> trackInfoList) {
        ArrayList<Boolean> results = new ArrayList<>(trackInfoList.size());
        for (TrackInfo trackInfo : trackInfoList) {
            log.info("track info: {}", trackInfo);

            if (!trackInfo.checkAllNotNull()) {
                results.add(false);
                continue;
            }

            Map<String, Long> sources = Stream.of(StringUtils.split(trackInfo.getSources(), ','))
                    .map(String::trim)
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toMap(
                            Function.identity(), source -> trackSourceDao.findIdByName(source)
                    ));

            Long timeStamp = Instant.now().toEpochMilli();

            for (Map.Entry<String, Long> entry : sources.entrySet()) {
                if (entry.getValue() != null) {

                    RawPlaylistTrackInfo rawPlaylistTrackInfo = RawPlaylistTrackInfo
                            .builder()
                            .artist(trackInfo.getArtist())
                            .title(trackInfo.getTitle())
                            .trackSourceId(entry.getValue())
                            .creationTime(timeStamp)
                            .build();

                    rawPlaylistTrackInfoDao.saveToStaging(rawPlaylistTrackInfo);
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
