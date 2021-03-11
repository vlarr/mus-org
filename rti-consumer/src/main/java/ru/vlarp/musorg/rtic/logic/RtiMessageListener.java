package ru.vlarp.musorg.rtic.logic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.musorg.commons.pojo.RtiMessage;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.rmql.utils.TopicNameList;
import ru.vlarp.musorg.sqltl.dao.RawTrackInfoSqltDao;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class RtiMessageListener {
    private TrackSourceDao trackSourceDao;
    private RawTrackInfoSqltDao rawTrackInfoSqltDao;

    @Autowired
    public void setTrackSourceDao(TrackSourceDao trackSourceDao) {
        this.trackSourceDao = trackSourceDao;
    }

    @Autowired
    public void setRawMusInfoDao(RawTrackInfoSqltDao rawTrackInfoSqltDao) {
        this.rawTrackInfoSqltDao = rawTrackInfoSqltDao;
    }

    @RabbitListener(queues = TopicNameList.RAW_TRACK_INFO)
    public void listenForSampleQueue(String message) {
        log.info("Received <" + message + ">");
        processRmiMessage(JsonUtils.fromJSON(RtiMessage.class, message));
    }

    public void processRmiMessage(RtiMessage rtiMessage) {
        log.info("process rtiMessage: {}", rtiMessage);

        Map<String, Long> sources = Stream.of(StringUtils.split(rtiMessage.getSources(), ','))
                .map(String::trim)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toMap(
                        Function.identity(), source -> trackSourceDao.findIdByName(source)
                ));

        Long timeStamp = Instant.now().toEpochMilli();

        for (Map.Entry<String, Long> entry : sources.entrySet()) {
            if (entry.getValue() != null) {
                rawTrackInfoSqltDao.save(rtiMessage.getArtist(),
                        rtiMessage.getAlbum(),
                        rtiMessage.getTrack(),
                        entry.getValue(),
                        timeStamp,
                        rtiMessage.getTags());

                trackSourceDao.updateLastModifiedTime(entry.getValue(), timeStamp);
            } else {
                log.warn("not find source: {}", entry.getKey());
            }
        }
    }
}
