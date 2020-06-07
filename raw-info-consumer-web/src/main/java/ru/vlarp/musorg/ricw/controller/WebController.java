package ru.vlarp.musorg.ricw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vlarp.musorg.commons.dao.TrackSourceDao;

@Controller
public class WebController {
    private TrackSourceDao trackSourceDao;

    @Autowired
    public void setTrackSourceDao(TrackSourceDao trackSourceDao) {
        this.trackSourceDao = trackSourceDao;
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/addTrackInfo")
    public String addTrackInfo(Model model) {
        model.addAttribute("activeSources", trackSourceDao.findActiveSourceNames());
        return "addTrackInfo";
    }
}