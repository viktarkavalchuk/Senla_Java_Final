package com.senla.course.announcementPlatform.controller;

import com.senla.course.announcementPlatform.dao.UserDao;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = AppInit.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {UserServiceImpl.class, UserDao.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDao userDao;

    @Test
    void registration() throws Exception {
        User user = new User();
        user.setUserName("Name");
        user.setLogin("Login");

        mockMvc.perform(post("/user?name=Andrey&email=Andrey@mail.com&telephone_Number=+375339998877&login=Admin10&password=password&ROLES=1, 2")
                .secure(true).content("STRING"));

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private User createTestUser(String name) {
        User user = new User();
        user.setUserName(name);
        userDao.create(user);
        return user;

    }
}