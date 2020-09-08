package ru.vlarp.musorg.rtic.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.commons.pojo.RawTrackInfo;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.rmql.utils.TopicNameList;
import ru.vlarp.musorg.rtic.service.RawInfoService;

@Component
@Slf4j
public class AppLogic {
    private RawInfoService rawInfoService;

    @Autowired
    public void setRawInfoService(RawInfoService rawInfoService) {
        this.rawInfoService = rawInfoService;
    }

    @RabbitListener(queues = TopicNameList.RAW_TRACK_INFO)
    public void listenForSampleQueue(String message) {
        log.info("Received <" + message + ">");
        rawInfoService.processRawTrackInfo(JsonUtils.fromJSON(RawTrackInfo.class, message));
    }
}
