package com.senla.course.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private static final Logger logger = LogManager.getLogger();

    @GetMapping("/")
    public String home () {
        return "/home";
    }

    @GetMapping("/index")
    public String index(Model model) {
        return "users/index";
    }
}
