package com.xszymo.hibernate.controllers.standard;

import com.xszymo.hibernate.controllers.chat.UserChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyUserChat;
import com.xszymo.hibernate.data.tables.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

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
        boolean canAdd;
        User user = (User) session.getAttribute("user");
        LinkedList<MyChat> chats = new LinkedList<>();
        for(MyUserChat x : UserChat.myChat) {
            for (MyChat x1 : x.myChat) {
                if (x.user.getLogin().equals(user.getLogin())) {
                    canAdd = true;
                    for (MyChat x2 : chats) {
                        if (x2.getId() == x1.getId())
                            canAdd = false;
                    }
                    if (canAdd)
                        chats.add(x1);
                }

                canAdd = false;
                for(User x4 : x.users) {
                    if(x4.getLogin().equals(user.getLogin())) {
                        canAdd = true;
                        for (MyChat x2 : chats) {
                            if (x2.getId() == x1.getId())
                                canAdd = false;
                        }
                    }
                }
                if (canAdd)
                    chats.add(x1);
            }
        }
        model.addAttribute("Ids", chats);
        return "UserChat";
    }

    @GetMapping("/loginPage")
    public String start5(HttpSession session) {
        System.out.println(session.getAttribute("user"));
        return "login";
    }
}
