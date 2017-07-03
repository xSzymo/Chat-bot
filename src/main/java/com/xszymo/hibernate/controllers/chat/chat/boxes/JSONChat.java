package com.xszymo.hibernate.controllers.chat.chat.boxes;


import java.util.LinkedList;

public class JSONChat {

    private String id;
    private String user;
    private String message;
    private LinkedList<String> messages = new LinkedList<String>();


    public JSONChat() {
    }

    public JSONChat(String asd) {
        this.id = asd;
    }

    public JSONChat(MyChat myChat) {
        this.id = myChat.id;
        this.user = myChat.user;
        this.message = myChat.message;
        this.messages = myChat.messages;
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
