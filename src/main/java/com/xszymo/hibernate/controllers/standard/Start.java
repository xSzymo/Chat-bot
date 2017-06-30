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

    @GetMapping("allChat")
    public String start1() {
        return "AllChat";
    }

    @GetMapping("chatRooms")
    public String start2() {
        return "ChatRooms";
    }

    @GetMapping("/chatBot")
    public String start3() {
        return "ChatBot";
    }
}
