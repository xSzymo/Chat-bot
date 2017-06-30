package com.xszymo.hibernate.controllers.standard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Start {
    @GetMapping
    public String start() {
        return "choose";
    }

    @GetMapping("bot")
    public String start1() {
        return "chatBot";
    }

    @GetMapping("all")
    public String start2() {
        return "allChat";
    }

    @GetMapping("rooms")
    public String start3() {
        return "chatRooms";
    }

    @GetMapping("user")
    public String start4() {
        return "userChat";
    }
}
