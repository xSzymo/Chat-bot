package com.xszymo.hibernate.configuration;


import com.xszymo.hibernate.controllers.tools.DAO;
import com.xszymo.hibernate.data.interfaces.UserService;
import com.xszymo.hibernate.data.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//(defaultAutowire = Autowire.BY_TYPE, defaultLazy = Lazy.FALSE)
public class Beans {
    @Bean
    public DAO dao() {
        return new DAO();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
