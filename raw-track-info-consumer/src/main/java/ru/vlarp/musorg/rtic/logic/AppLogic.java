package ru.vlarp.musorg.rtic.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.commons.pojo.RmiMessage;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.rmql.utils.TopicNameList;
import ru.vlarp.musorg.rtic.service.RmiMessageService;

@Component
@Slf4j
public class AppLogic {
    private RmiMessageService rmiMessageService;

    @Autowired
    public void setRmiMessageService(RmiMessageService rmiMessageService) {
        this.rmiMessageService = rmiMessageService;
    }

    @RabbitListener(queues = TopicNameList.RAW_TRACK_INFO)
    public void listenForSampleQueue(String message) {
        log.info("Received <" + message + ">");
        rmiMessageService.processRmiMessage(JsonUtils.fromJSON(RmiMessage.class, message));
    }
}
