package ru.vlarp.musorg.mbzta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AppController {
    @ResponseBody
    @GetMapping(path = "/home", produces = "application/json")
    public String home() {
        return "{result=\"ok\"}";
    }
}
