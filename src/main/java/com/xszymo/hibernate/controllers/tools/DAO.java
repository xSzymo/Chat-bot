package com.xszymo.hibernate.controllers.tools;

import com.xszymo.hibernate.data.interfaces.UserDao;
import com.xszymo.hibernate.data.interfaces.UserService;
import com.xszymo.hibernate.data.services.UserServiceImpl;
import com.xszymo.hibernate.data.tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

@Service
public class DAO {

    @Autowired
    private UserService userService;

    public User login(String login, String password) {
        if(isUser(login) == false)
            return null;
        User user = findByLogin(login);

        if(user.getPassword().equals(password))
            return user;

        return null;
    }

    private boolean isUser(String login) {
        return (findByLogin(login) != null) ? true : false;
    }

    public User findByLogin(String login) {
        Collection<User> allUsers = userService.findAllUsers();
        for(User x : allUsers)
            if(x.getUsername().equals(login))
                return x;
        return null;
    }
}
