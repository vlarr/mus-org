package ru.vlarp.musorg.rtic.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.commons.pojo.RmiMessage;
import ru.vlarp.musorg.sqltl.dao.RawMusInfoDao;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class RmiMessageService {
    private TrackSourceDao trackSourceDao;
    private RawMusInfoDao rawMusInfoDao;

    @Autowired
    public void setTrackSourceDao(TrackSourceDao trackSourceDao) {
        this.trackSourceDao = trackSourceDao;
    }

    @Autowired
    public void setRawMusInfoDao(RawMusInfoDao rawMusInfoDao) {
        this.rawMusInfoDao = rawMusInfoDao;
    }

    public void processRmiMessage(RmiMessage rmiMessage) {
        log.info("process rmiMessage: {}", rmiMessage);

        Map<String, Long> sources = Stream.of(StringUtils.split(rmiMessage.getSources(), ','))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toMap(
                        Function.identity(), source -> trackSourceDao.findIdByName(source)
                ));

        Long timeStamp = Instant.now().toEpochMilli();

        for (Map.Entry<String, Long> entry : sources.entrySet()) {
            if (entry.getValue() != null) {
                rawMusInfoDao.save(rmiMessage.getArtist(),
                        rmiMessage.getAlbum(),
                        rmiMessage.getTrack(),
                        entry.getValue(),
                        timeStamp,
                        rmiMessage.getTags());

                trackSourceDao.updateLastModifiedTime(entry.getValue(), timeStamp);
            } else {
                log.warn("not find source: {}", entry.getKey());
            }
        }
    }


}
