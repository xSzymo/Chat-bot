package com.xszymo.hibernate.controllers.chat.chat.boxes;


import java.util.LinkedList;

public class ChatJSON {
    public String id;
    public LinkedList<String> messages = new LinkedList<String>();
    public String message;
    public String user;
}
