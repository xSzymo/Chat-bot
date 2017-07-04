package com.xszymo.hibernate.controllers.standard;

import com.xszymo.hibernate.controllers.chat.UserChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyUserChat;
import com.xszymo.hibernate.controllers.tools.DAO;
import com.xszymo.hibernate.data.interfaces.UserService;
import com.xszymo.hibernate.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

@Controller
public class Login {
    @Autowired
    UserService userService;
    @Autowired
    private DAO dao;

    @RequestMapping(value = "userLogin", method = RequestMethod.POST)
    public String loginUser(@RequestParam(name = "login", required = false, defaultValue = "") String login,
                            @RequestParam(name = "password", required = false, defaultValue = "") String password,
                            HttpServletRequest request, Model model, HttpServletResponse response) {
        if (login == null || login.equals("") && password == null || password.equals(""))
            return "login";

        User user = dao.login(login, password);
        if(user == null)
            return "login";

        request.getSession().setAttribute("user", user);
        model.addAttribute("canShow", true);
        return "choose";
    }


        @RequestMapping(value = "logout", method = RequestMethod.GET)
        public String logout (HttpServletRequest request, HttpServletResponse response, Model model){

            request.getSession().setAttribute("user", null);
            return "choose";
        }

        @RequestMapping(value = "userRegistration", method = RequestMethod.POST)
        public String userRegistration (@RequestParam(name = "login", required = false, defaultValue = "") String login,
                @RequestParam(name = "password", required = false, defaultValue = "") String password,
                HttpServletRequest request, Model model) {
            Iterable<User> users = userService.findAllUsers();

            for (User x : users) {
                if (x.getLogin() != null)
                    if (x.getLogin().equals(login)) {
                        model.addAttribute("msg", "That user name allready exist  !");
                        return "registration";
                    }
            }

            User user = new User(login, password);
            userService.persist(user);
            MyUserChat a = new MyUserChat();
            a.user = user;
            a.myChat = new LinkedList<>();
            UserChat.myChat.add(a);

            model.addAttribute("msg", "Successful registration  !");
            return "login";
        }
    }
