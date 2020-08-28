package ru.vlarp.musorg.rtiwh.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;
import ru.vlarp.musorg.commons.pojo.RawTrackInfo;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.rmql.utils.TopicNameList;
import ru.vlarp.musorg.rtiwh.logic.AppLogic;

import java.time.Instant;
import java.util.List;

@Controller
@Slf4j
public class AppController {
    private AppLogic appLogic;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setAppLogic(AppLogic appLogic) {
        this.appLogic = appLogic;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping(path = "/tryParseTrackInfo", consumes = "text/plain", produces = "application/json")
    public ResponseEntity<ParseTrackInfoResult> tryParseTrackInfoString(@RequestBody String rawTrackInfo) {
        try {
            ParseTrackInfoResult result = appLogic.tryParseTrackInfo(rawTrackInfo);
            return ResponseEntity
                    .status(result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(result);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(path = "/saveRawTrackInfo", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> saveRawTrackInfo(@RequestBody List<RawTrackInfo> rawTrackInfo) {
        log.info("call consumeTrackInfos. trackInfo={}", rawTrackInfo);
        for (RawTrackInfo trackInfo : rawTrackInfo) {
            rabbitTemplate.convertAndSend(TopicNameList.RAW_TRACK_INFO, JsonUtils.toJSON(trackInfo));
        }
        return ResponseEntity.ok(String.format("{\"result\":\"ok\",\"timestamp\"=%d}", Instant.now().toEpochMilli()));
    }
}
