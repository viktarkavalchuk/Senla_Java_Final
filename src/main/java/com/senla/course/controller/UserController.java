package com.senla.course.controller;

import com.senla.course.announcementPlatform.dao.UserDao;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.security.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;


@Controller
@RequestMapping("/root")
public class UserController {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserDao userDao;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String index(Model model) {
        System.out.println(userService.getById(0).getLogin());
        return "users/index";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/home")
    public String index() {

        Set<Role> roles = userDao.getById(1).getRoles();
        System.out.println(roles);

        User user = new User();
        user.setUserName("USER_1213");
        user.setEmail("ema12il@email.com");
        user.setTelephoneNumber("+37533339998877");
        user.setLogin("login1");
        user.setPassword("pasword1");
        user.setRoles(roles);
        userService.create(user);


//        System.out.println(user.getUserName());
        return "home";
    }
}
