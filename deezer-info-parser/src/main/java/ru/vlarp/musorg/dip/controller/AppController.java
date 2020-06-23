package ru.vlarp.musorg.dip.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlarp.musorg.dip.logic.AppLogic;

@Controller
public class AppController {
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    private AppLogic appLogic;

    @Autowired
    public void setAppLogic(AppLogic appLogic) {
        this.appLogic = appLogic;
    }

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Hello!";
    }

    @GetMapping("/grabDeezerPlaylist")
    @ResponseBody
    public String grabDeezerPlaylist(@RequestParam Long playlistId, @RequestParam(required = false) String playlistName) {
        log.info("call grabDeezerPlaylist, playlistId={}, playlistName={}", playlistId, playlistName);

        if (playlistName == null) {
            playlistName = "DEEZER_MISC";
        }

        return appLogic.processDeezerPlaylist(playlistId, playlistName) == 0 ? "OK" : "ERROR";
    }
}
