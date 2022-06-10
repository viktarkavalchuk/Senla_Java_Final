package com.senla.course.rest.builder;

import com.senla.course.announcementPlatform.model.User;

public class UserBuilder {

    public static User userBuilder() {

        User user = new User();
        user.setUserName("UserName");
        user.setLogin("LOGIN");
        user.setPassword("password");
        user.setEmail("email@email.com");
        user.setTelephoneNumber("+123456789999");

        return user;
    }
}
