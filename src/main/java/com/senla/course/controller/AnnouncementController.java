package com.senla.course.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
    private static final Logger logger = LogManager.getLogger();
    private final AnnouncementServiceImpl announcementService;

    public AnnouncementController(AnnouncementServiceImpl announcementService) {
        this.announcementService = announcementService;
    }


    @GetMapping("/getAll")
    public String getAnnouncement(Model model){
        List<Announcement> vip = announcementService.getVip();
        List<Announcement> notVip = announcementService.getNotVip();
        Collections.sort(notVip, Announcement.COMPARE_BY_RATING.reversed());

        model.addAttribute("NotVip", notVip);
        model.addAttribute("Vip", vip);
        return "announcement/all";
    }
}
