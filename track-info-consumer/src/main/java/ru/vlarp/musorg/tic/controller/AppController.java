package ru.vlarp.musorg.tic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;
import ru.vlarp.musorg.commons.pojo.TrackInfo;
import ru.vlarp.musorg.tic.logic.AppLogic;

import java.util.List;

@Controller
public class AppController {
    private AppLogic logic;

    @Autowired
    public void setLogic(AppLogic logic) {
        this.logic = logic;
    }

    @GetMapping(path = "/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("TEST: OK");
    }

    @PostMapping(path = "/tryParseTrackInfo", consumes = "text/plain", produces = "application/json")
    public ResponseEntity<ParseTrackInfoResult> tryParseTrackInfo(@RequestBody String rawTrackInfo) {
        try {
            ParseTrackInfoResult result = logic.tryParseTrackInfo(rawTrackInfo);
            return ResponseEntity
                    .status(result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(result);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping(path = "/consumeTrackInfos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Boolean>> consumeTrackInfos(@RequestBody List<TrackInfo> trackInfoList) {
        return ResponseEntity.ok(logic.consumeTrackInfos(trackInfoList));
    }
}
