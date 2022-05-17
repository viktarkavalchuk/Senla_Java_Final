package com.senla.course.controller;

import com.senla.course.announcementPlatform.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    private static final Logger logger = LogManager.getLogger();

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService user;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", user.getAll());
        System.out.println("USER: " + user.getAll().get(0).getEmail());
//        System.out.println("Announcement: " + announcement.getAll().get(0).getName());

        return "users/index";
    }
}
