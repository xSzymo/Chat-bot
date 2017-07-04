package com.xszymo.hibernate.controllers.chat.chat.boxes;

import com.xszymo.hibernate.data.tables.User;

import java.util.LinkedList;

public class MyUserChat {

    public User user;
    public LinkedList<MyChat> myChat = new LinkedList<MyChat>();
}
