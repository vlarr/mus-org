package ru.vlarp.musorg.ricw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vlarp.musorg.commons.dao.RawPlaylistTrackInfoDao;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;
import ru.vlarp.musorg.commons.pojo.RawPlaylistTrackInfo;
import ru.vlarp.musorg.commons.pojo.TrackInfo;
import ru.vlarp.musorg.commons.service.RawInfoService;

import java.util.List;

@Controller
public class AppController {
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    private RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao;

    private RawInfoService rawInfoService;

    @Autowired
    public void setRawPlaylistTrackInfoDao(RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao) {
        this.rawPlaylistTrackInfoDao = rawPlaylistTrackInfoDao;
    }

    @Autowired
    public void setRawInfoService(RawInfoService rawInfoService) {
        this.rawInfoService = rawInfoService;
    }

    @PostMapping(path = "/consumeRawTrackInfo", consumes = "text/plain", produces = "application/json")
    public ResponseEntity<ParseTrackInfoResult> remoteConsumeRawTrackInfo(@RequestBody String rawTrackInfo) {
        log.info("call tryParseTrackInfo. rawTrackInfo={}", rawTrackInfo);
        try {
            ParseTrackInfoResult result = rawInfoService.tryParseTrackInfo(rawTrackInfo);
            return ResponseEntity
                    .status(result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(result);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(path = "/consumeTrackInfos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Boolean>> remoteConsumeTrackInfos(@RequestBody List<TrackInfo> trackInfo) {
        log.info("call consumeTrackInfos. trackInfo={}", trackInfo);
        return ResponseEntity.ok(rawInfoService.consumeTrackInfos(trackInfo));
    }

    @PostMapping(path = "/lastTrackInfos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<RawPlaylistTrackInfo>> getLastTrackInfoByLimit(@RequestBody Integer trackLimit) {
        log.info("call lastTrackInfos. trackLimit={}", trackLimit);
        return ResponseEntity.ok(rawPlaylistTrackInfoDao.findLastByLimit(trackLimit));
    }
}
