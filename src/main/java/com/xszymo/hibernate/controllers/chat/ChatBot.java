package com.xszymo.hibernate.controllers.chat;

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
@RequestMapping("/chatWithBot")
public class ChatBot {

    @Resource(name = "answerMessageService")
    AnswersMessageService answer;

    @Resource(name = "questionMessageService")
    QuestionsMessageService question;

    @Autowired
    UserService userService;

    public LinkedList<MyChat> myChat = new LinkedList<MyChat>();

    @GetMapping("createChatId")
    public String createChatId() {
        MyChat a = new MyChat();
        a.id = createId();
        myChat.add(a);
        return a.id;
    }

    @PostMapping("checkChatId")
    public @ResponseBody
    String checkChatId(@RequestBody MyChat chat) {
        String chatId = chat.id;
        System.out.println("xcbxcb id " + chatId);

        MyChat existingChat = findOne(chatId);
        if (existingChat == null)
            return createChatId();

        return chatId;
    }

    @GetMapping
    public LinkedList<String> getMessages(@RequestParam String chatId, @RequestParam LinkedList<String> messages, @RequestParam String user) {
        MyChat chat = findOne(chatId);

        if (chat == null || chat.messages.size() == messages.size())
            return messages;

        return chat.messages;
    }


    @PostMapping
    public void postMessage(@RequestParam String chatId, @RequestParam String message, @RequestParam String user) {
        System.out.println("chxcbcxbxcat id " + chatId);
        MyChat chat = findOne(chatId);
        if (chat == null)
            return;
        System.out.println("chaasfvxxt id " + chatId);

        Question a = question.findByMessage(message);
        String botMessage = "Not implemented yet :(";

        if (a != null) {
            System.out.println(a.getMessage());
            long b = 0;
            for (Answer x : a.getAnswers())
                if (b <= x.getCounter()) {
                    botMessage = x.getMessage();
                    b = x.getCounter();
                }
        }
        System.out.println("chbcxat id " + chatId);

        chat.messages.add("You : " + message);
        chat.messages.add("Bot : " + botMessage);
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
