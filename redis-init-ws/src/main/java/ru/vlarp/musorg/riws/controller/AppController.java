package ru.vlarp.musorg.riws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlarp.musorg.riws.logic.AppLogic;

@Controller
public class AppController {
    private AppLogic appLogic;

    @Autowired
    public void setAppLogic(AppLogic appLogic) {
        this.appLogic = appLogic;
    }

    @ResponseBody
    @GetMapping(path = "/init-redis", produces = "application/json")
    public String initRedis() {
        appLogic.initRedis();
        return "Ok";
    }

    @ResponseBody
    @GetMapping(path = "/home", produces = "application/json")
    public String home() {
        return "{\"home\"=\"this\"}";
    }
}
