package com.senla.course.controller;

import com.senla.course.announcementPlatform.model.Rating;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.RatingServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {

    private static final Logger logger = LogManager.getLogger();


    @Autowired
    private UserServiceImpl user;
    @Autowired
    private AnnouncementServiceImpl announcement;
    @Autowired
    private ChatServiceImpl chatService;
    @Autowired
    private RatingServiceImpl rating;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", user.getAll());
        System.out.println("USER: " + user.getAll().get(0).getEmail());
        System.out.println("Announcement: " + announcement.getAll().get(1).getComments().get(0).getBodyComment());
        System.out.println("Chat : " + chatService.getById(0).getMessage());
        System.out.println("Rating " + rating.getById(0).getRating());
        return "users/index";
    }
}
