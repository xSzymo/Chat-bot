package com.xszymo.hibernate.controllers.chat.chat.boxes;

import java.util.LinkedList;

public class MyChat {

    public String id;
    public String user;
    public String message;
    public LinkedList<String> messages = new LinkedList<String>();


    public MyChat() {
    }

    public MyChat(String asd) {
        this.id = asd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<String> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<String> messages) {
        this.messages = messages;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
