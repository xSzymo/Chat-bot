package com.xszymo.hibernate.controllers.chat;

import com.xszymo.hibernate.controllers.chat.chat.boxes.JSONChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyUserChat;
import com.xszymo.hibernate.controllers.chat.tools.Coder;
import com.xszymo.hibernate.data.tables.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

@RestController
@RequestMapping("/userChat")
public class UserChat {
    public static LinkedList<MyUserChat> myChat = new LinkedList<MyUserChat>();

    @GetMapping("createChatId")
    public @ResponseBody
    JSONChat createChatId(HttpSession session) {
        User user = getUser(session);

        if (user == null)
            return null;

        MyChat chat = createNewChat(user);
        return new JSONChat(chat);
    }

    @GetMapping("test")
    public @ResponseBody
    LinkedList<MyUserChat> halo() {
        LinkedList<MyUserChat> a = new LinkedList<>();
        myChat.forEach(x -> a.add(x));
        return a;
    }

    @PostMapping("checkChatId")
    public @ResponseBody
    JSONChat checkChatId(@RequestBody JSONChat clientChat, HttpSession session) {
        User user = getUser(session);
        //clientChat.setUser(user.getLogin());


        MyChat existingChat = findByChatId(clientChat.getId());
        if (existingChat == null)
            return createChatId(session);
        System.out.println("id : " + existingChat.getId());

        return new JSONChat(existingChat);
    }

    @PostMapping("getMessages")
    public LinkedList<String> getMessages(@RequestBody JSONChat clientChat, HttpSession session) {
        User user = getUser(session);
        //clientChat.setUser(user.getLogin());

        MyChat chat = findByChatId(clientChat.getId());
        if (chat == null)
            return clientChat.getMessages();
        System.out.println(chat.getId());

        return chat.messages;
    }


    @PostMapping("postMessage")
    public void postMessage(@RequestBody JSONChat clientChat, HttpSession session) {
        User user = getUser(session);
        clientChat.setUser(user.getLogin());

        MyChat chat = findByChatId(clientChat.getId());
        if (chat == null)
            return;

        System.out.println(clientChat.getMessage());

        chat.messages.add(clientChat.getUser() + " : " + clientChat.getMessage());
    }

    public User getUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }


    public MyChat createNewChat(User user) {
        for (MyUserChat x : myChat) {
            if (x.user.getLogin().equals(user.getLogin())) {

                MyChat c = new MyChat();

                c.setId(createId(user));
                c.setUser(user.getLogin());
                x.myChat.add(c);
                return c;
            }
        }
        MyUserChat a = new MyUserChat();
        a.user = user;

        MyChat b = new MyChat();

        b.setId(createId(user));
        b.setUser(user.getLogin());
        a.myChat.add(b);

        myChat.add(a);
        return b;
    }


    public String createId(User user) {
        String code;

        boolean cannotLeave;
        outerLoop:
        do {
            cannotLeave = false;
            code = Coder.coder();
            for (MyUserChat x1 : myChat)
                for (MyChat x : x1.myChat) {
                    if (x.id.equals(code))
                        cannotLeave = true;
                }
            //  }
        } while (cannotLeave);

        return code;
    }

    public MyUserChat findByUser(User user) {
        LinkedList<MyUserChat> b = new LinkedList<>();
        for (MyUserChat x : myChat)
            if (user.getLogin().equals(x.user.getLogin()))
                return x;

        return null;
    }


    public MyChat findByChatId(String id) {
        for (MyUserChat x : myChat)
            for (MyChat x1 : x.myChat)
                if (x1.getId().equals(id)) {
                    System.out.println("not null ");
                    return x1;
                }
        System.out.println("NULL");
        return null;
    }
}
