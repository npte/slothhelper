package ru.npte.sloth.slothhelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.npte.sloth.slothhelper.service.SlothWebSiteParsingService;

@Controller
@RequestMapping("/hello")
public class TestController {

    @Autowired
    private SlothWebSiteParsingService slothWebSiteParsingService;

    @RequestMapping
    @ResponseBody
    public String getHello() {
        try {
            slothWebSiteParsingService.getAucItemsNames();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "hello wolrd";
    }
}