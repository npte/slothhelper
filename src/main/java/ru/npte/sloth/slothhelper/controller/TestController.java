package ru.npte.sloth.slothhelper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class TestController {

    @RequestMapping
    @ResponseBody
    public String getHello() {
        return "hello wolrd";
    }
}