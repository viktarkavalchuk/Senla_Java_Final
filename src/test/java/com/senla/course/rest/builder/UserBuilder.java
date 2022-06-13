package com.senla.course.rest.builder;

import com.senla.course.announcementPlatform.model.User;

public class UserBuilder {

    public static User userBuilderUser1() {

        User user = new User();
        user.setUserName("UserName");
        user.setLogin("User1");
        user.setPassword("password");
        user.setEmail("email1@email.com");
        user.setTelephoneNumber("+123456789999");

        return user;
    }
    public static User userBuilderUser2() {

        User user = new User();
        user.setUserName("UserName");
        user.setLogin("User2");
        user.setPassword("password");
        user.setEmail("email2@email.com");
        user.setTelephoneNumber("+123456789990");

        return user;
    }
}
