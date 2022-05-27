package com.senla.course.controller;

import com.senla.course.announcementPlatform.dao.UserDao;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@FixMethodOrder
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServiceImpl.class, UserDao.class} )
class UserControllerTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void registration() {
        assertEquals(0, 0);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}