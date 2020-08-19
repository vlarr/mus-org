package ru.vlarp.musorg.rtiwh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vlarp.musorg.rlib.dao.TrackSourceRedisDao;

@Controller
public class WebController {
    private TrackSourceRedisDao trackSourceRedisDao;

    @Autowired
    public void setTrackSourceDao(TrackSourceRedisDao trackSourceRedisDao) {
        this.trackSourceRedisDao = trackSourceRedisDao;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/addTrackInfo")
    public String addTrackInfo(Model model) {
        model.addAttribute("activeSources", trackSourceRedisDao.findAll());
        return "addTrackInfo";
    }
}
