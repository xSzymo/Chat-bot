package com.xszymo.hibernate.controllers.standard;

import com.xszymo.hibernate.controllers.chat.UserChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyUserChat;
import com.xszymo.hibernate.data.tables.User;
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
    public String start4(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        for(MyUserChat x : UserChat.myChat)
            if(x.user.getLogin().equals(user.getLogin()))
                model.addAttribute("Ids", x.myChat);

        return "UserChat";
    }

    @GetMapping("/loginPage")
    public String start5(HttpSession session) {
        System.out.println(session.getAttribute("user"));
        return "login";
    }
}
