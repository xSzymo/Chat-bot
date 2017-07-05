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

        if(user == null)
            return null;

        MyChat chat = createNewChat(user);
        return new JSONChat(chat);
    }

    @GetMapping("test")
    public @ResponseBody LinkedList<MyUserChat> halo() {
        LinkedList<MyUserChat> a = new LinkedList<>();
        myChat.forEach(x -> a.add(x));
        return a;
    }

    @PostMapping("checkChatId")
    public @ResponseBody
    JSONChat checkChatId(@RequestBody JSONChat clientChat, HttpSession session) {
        User user = getUser(session);
        clientChat.setUser(user.getLogin());


        MyChat existingChat = findOne(clientChat.getId(), user);
        if (existingChat == null)
            return createChatId(session);

        return new JSONChat(existingChat);
    }

    @PostMapping("getMessages")
    public LinkedList<String> getMessages(@RequestBody JSONChat clientChat, HttpSession session) {
        User user = getUser(session);
        clientChat.setUser(user.getLogin());
        MyUserChat chaterino = findByUser(user);

        MyChat chat = findOne(clientChat.getId(), user);
        if (chat == null)
            return clientChat.getMessages();

        return chat.messages;
    }


    @PostMapping("postMessage")
    public void postMessage(@RequestBody JSONChat clientChat, HttpSession session) {
        User user = getUser(session);
        clientChat.setUser(user.getLogin());

        MyChat chat = findOne(clientChat.getId(), user);
        if (chat == null)
            return;

        System.out.println(clientChat.getMessage());

        chat.messages.add(clientChat.getUser() + " : " + clientChat.getMessage());
    }

    public User getUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }


    public MyChat createNewChat(User user) {
        for(MyUserChat x : myChat) {
            if(x.user.getLogin().equals(user.getLogin())) {

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
        MyUserChat chaterino = findByUser(user);
        if(chaterino == null) {
            MyUserChat myUserChat = new MyUserChat();
            myUserChat.user = user;
            chaterino = myUserChat;
        }

        boolean cannotLeave;
        outerLoop:
        do {
            cannotLeave = false;
            code = Coder.coder();
            //if(chaterino != null) {
           //     if(chaterino.myChat.)
                for (MyChat x : chaterino.myChat) {
                    if (x.id.equals(code))
                        cannotLeave = true;
                }
          //  }
        } while (cannotLeave);

        return code;
    }

    public MyChat findOne(String chatId, User user) {
        MyUserChat chaterino = findByUser(user);

        try {
            for (MyChat x : chaterino.myChat) {
                if (x.id.equals(chatId))
                    return x;
            }
        } catch (NullPointerException e) {
            //e
        }
        return null;
    }

    public MyUserChat findByUser(User user) {
        for(MyUserChat x : myChat)
            if(user.getLogin().equals(x.user.getLogin()))
                return x;
            return null;
    }
}
