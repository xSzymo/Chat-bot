package com.xszymo.hibernate.controllers.chat;

import com.xszymo.hibernate.controllers.chat.chat.boxes.JSONChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyChat;
import com.xszymo.hibernate.controllers.chat.tools.Coder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/chatRoom")
public class ChatRooms {
    public LinkedList<MyChat> myChat = new LinkedList<MyChat>();

    @GetMapping("createChatId")
    public @ResponseBody
    JSONChat createChatId() {
        MyChat chat = createNewChat();
        return new JSONChat(chat);
    }

    @PostMapping("checkChatId")
    public @ResponseBody
    JSONChat checkChatId(@RequestBody JSONChat clientChat) {
        MyChat existingChat = findOne(clientChat.getId());
        if (existingChat == null)
            return createChatId();

        return new JSONChat(existingChat);
    }

    @PostMapping("getMessages")
    public LinkedList<String> getMessages(@RequestBody JSONChat clientChat) {
        MyChat chat = findOne(clientChat.getId());
        if (chat == null)
            return clientChat.getMessages();

        return chat.messages;
    }


    @PostMapping("postMessage")
    public void postMessage(@RequestBody JSONChat clientChat) {
        MyChat chat = findOne(clientChat.getId());
        if (chat == null)
            return;

        chat.messages.add(clientChat.getUser() + " : " + clientChat.getMessage());
    }


    public MyChat createNewChat() {
        MyChat a = new MyChat();
        a.id = createId();
        myChat.add(a);
        return a;
    }


    public String createId() {
        String code;

        boolean cannotLeave;
        outerLoop:
        do {
            cannotLeave = false;
            code = Coder.coder();
            for (MyChat x : myChat) {
                if (x.id.equals(code))
                    cannotLeave = true;
            }
        } while (cannotLeave);

        return code;
    }

    public MyChat findOne(String chatId) {
        for (MyChat x : myChat) {
            if (x.id.equals(chatId))
                return x;
        }
        return null;
    }
}
