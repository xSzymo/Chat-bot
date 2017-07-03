package com.xszymo.hibernate.controllers.chat;

import com.xszymo.hibernate.controllers.chat.chat.boxes.JSONChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyChat;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/chat")
public class AllChat {

    public MyChat myChat = new MyChat();

    @GetMapping
    public @ResponseBody
    LinkedList<String> getMessages() {
        return myChat.messages;
    }

    @PostMapping
    public void postMessage(@RequestBody JSONChat chat) {
        if (myChat == null)
            return;
        if (chat.getMessages() == null)
            return;

        myChat.messages.add(chat.getUser() + " : " + chat.getMessage());
    }
}
