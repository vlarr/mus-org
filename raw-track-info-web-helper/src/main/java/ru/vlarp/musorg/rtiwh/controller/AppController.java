package ru.vlarp.musorg.rtiwh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import ru.vlarp.musorg.commons.dao.RawPlaylistTrackInfoDao;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;
import ru.vlarp.musorg.commons.pojo.RawPlaylistTrackInfo;
import ru.vlarp.musorg.commons.pojo.TrackInfo;

import java.util.List;

@Controller
public class AppController {
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    //    @Value("${tic.server.port}")
    private static final Integer TIC_PORT = 8080;

    @Value("${TIC_ADDRESS}")
    private String ticAddress;

    private RestTemplateBuilder restTemplateBuilder;

    private RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao;

    @Autowired
    public void setRestTemplateBuilder(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Autowired
    public void setRawPlaylistTrackInfoDao(RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao) {
        this.rawPlaylistTrackInfoDao = rawPlaylistTrackInfoDao;
    }

    @PostMapping(path = "/consumeRawTrackInfo", consumes = "text/plain", produces = "application/json")
    public ResponseEntity<ParseTrackInfoResult> remoteConsumeRawTrackInfo(@RequestBody String rawTrackInfo) {
        log.info("call consumeRawTrackInfo. rawTrackInfo={}", rawTrackInfo);
        RestTemplate restTemplate = restTemplateBuilder.build();
        String ticUrl = String.format("http://%s:%d/tryParseTrackInfo", ticAddress, TIC_PORT);

        return restTemplate.postForEntity(
                ticUrl,
                new HttpEntity<>(rawTrackInfo),
                ParseTrackInfoResult.class
        );
    }

    @PostMapping(path = "/consumeTrackInfos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Boolean[]> remoteConsumeTrackInfos(@RequestBody List<TrackInfo> trackInfo) {
        log.info("call consumeTrackInfos. trackInfo={}", trackInfo);
        RestTemplate restTemplate = restTemplateBuilder.build();
        String ticUrl = String.format("http://%s:%d/consumeTrackInfos", ticAddress, TIC_PORT);

        return restTemplate.postForEntity(
                ticUrl,
                new HttpEntity<>(trackInfo),
                Boolean[].class
        );
    }

    @PostMapping(path = "/lastTrackInfos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<RawPlaylistTrackInfo>> getLastTrackInfoByLimit(@RequestBody Integer trackLimit) {
        log.info("call lastTrackInfos. trackLimit={}", trackLimit);
        return ResponseEntity.ok(rawPlaylistTrackInfoDao.findLastByLimit(trackLimit));
    }
}
