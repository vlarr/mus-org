package ru.vlarp.musorg.rtiwh.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vlarp.musorg.commons.pojo.RtiMessage;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.rmql.utils.TopicNameList;
import ru.vlarp.musorg.rtiwh.logic.RtiParser;
import ru.vlarp.musorg.rtiwh.pojo.RtiParseResult;

@Controller
@Slf4j
public class AppController {
    private RtiParser rtiParser;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRtiParser(RtiParser rtiParser) {
        this.rtiParser = rtiParser;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping(path = "/tryParseRawTrackInfo", consumes = "text/plain", produces = "application/json")
    public ResponseEntity<RtiParseResult> tryParseRawTrackInfoString(@RequestBody String rawTrackInfo) {
        try {
            RtiParseResult result = rtiParser.tryParseTrackInfo(rawTrackInfo);
            return ResponseEntity
                    .status(result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(result);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(path = "/saveRawTrackInfo", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RtiMessage> saveRawTrackInfo(@RequestBody RtiMessage rtiMessage) {
        log.info("call consumeTrackInfos. rtiMessage={}", rtiMessage);

        try {
            rabbitTemplate.convertAndSend(TopicNameList.RAW_TRACK_INFO, JsonUtils.toJSON(rtiMessage));
            return ResponseEntity.ok(rtiMessage);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
