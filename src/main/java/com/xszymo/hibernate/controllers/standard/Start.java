package com.xszymo.hibernate.controllers.standard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class Start {
    @GetMapping
    public String start(HttpSession session, Model model) {
        boolean canShow = false;
        if(session.getAttribute("user") != null)
            canShow = true;

        model.addAttribute("canShow", canShow);
        return "choose";
    }

    @GetMapping("allChat")
    public String start1() {
        return "AllChat";
    }

    @GetMapping("registration")
    public String start5() {
        return "registration";
    }

    @GetMapping("chatRooms")
    public String start2() {
        return "ChatRooms";
    }

    @GetMapping("/chatBot")
    public String start3() {
        return "ChatBot";
    }

    @GetMapping("/userChat")
    public String start4() {

        return "UserChat";
    }

    @GetMapping("/loginPage")
    public String start5(HttpSession session) {
        System.out.println(session.getAttribute("user"));
        return "login";
    }
}
