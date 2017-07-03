package com.xszymo.hibernate.controllers.chat;

import com.xszymo.hibernate.controllers.chat.chat.boxes.JSONChat;
import com.xszymo.hibernate.controllers.chat.chat.boxes.MyChat;
import com.xszymo.hibernate.controllers.chat.tools.Coder;
import com.xszymo.hibernate.data.interfaces.AnswersMessageService;
import com.xszymo.hibernate.data.interfaces.QuestionsMessageService;
import com.xszymo.hibernate.data.interfaces.UserService;
import com.xszymo.hibernate.data.tables.Answer;
import com.xszymo.hibernate.data.tables.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;

@RestController
@RequestMapping("/chatWithUser")
public class UserChat {

    @Resource(name = "answerMessageService")
    AnswersMessageService answer;

    @Resource(name = "questionMessageService")
    QuestionsMessageService question;

    @Autowired
    UserService userService;

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

        if (chat == null || chat.messages.size() == clientChat.getMessages().size())
            return clientChat.getMessages();

        return chat.messages;
    }


    @PostMapping("postMessage")
    public void postMessage(@RequestBody JSONChat clientChat) {
        MyChat chat = findOne(clientChat.getId());
        if (chat == null)
            return;

        Question a = question.findByMessage(clientChat.getMessage());
        String botMessage = "Not implemented yet :(";

        if (a != null) {
            long b = 0;
            for (Answer x : a.getAnswers())
                if (b <= x.getCounter()) {
                    botMessage = x.getMessage();
                    b = x.getCounter();
                }
        }

        chat.messages.add("You : " + clientChat.getMessage());
        chat.messages.add("Bot : " + botMessage);
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
