package com.xszymo.hibernate.controllers.chat;

import com.xszymo.hibernate.controllers.chat.chat.boxes.ChatJSON;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyChat;
import com.xszymo.hibernate.controllers.chat.tools.Coder;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;

@RestController
@RequestMapping("/chatRoom")
public class ChatRooms {
    public LinkedList<MyChat> myChat = new LinkedList<MyChat>();

    @GetMapping("createChatId")
    public @ResponseBody String createChatId() {
        MyChat a = new MyChat();
        a.id = createId();
        myChat.add(a);
        return a.id;
    }

    @PostMapping("checkChatId")
    public @ResponseBody
    String checkChatId(@RequestBody ChatJSON clientChat) {
        MyChat existingChat = findOne(clientChat.id);
        if (existingChat == null)
            return createChatId();

        return clientChat.id;
    }

    @GetMapping
    public LinkedList<String> getMessages(@RequestParam String chatId, @RequestParam LinkedList<String> messages, @RequestParam String user) {
        System.out.println("here");
        MyChat chat = findOne(chatId);
        if (chat == null || chat.messages.size() == messages.size())
            return messages;

        return chat.messages;
    }


    @PostMapping
    public void postMessage(@RequestParam String chatId, @RequestParam String message, @RequestParam String user) {
        MyChat chat = findOne(chatId);
        if (chat == null)
            return;

        chat.messages.add(user + " : " + message);
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
