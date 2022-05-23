package com.senla.course.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/getByName")
    public String getAnnouncementByName(@RequestParam String name, Model model) {
        List<Announcement> getByName = announcementService.getAll()
                .stream().filter(x -> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        model.addAttribute("GetByName", getByName);

        return "announcement/byName";
    }

    @GetMapping("/getByNameAndPrice")
    public String getAnnouncementByNameAndPrice(@RequestParam String name, Integer price, Model model) {
        List<Announcement> getByNameAndPrice = announcementService.getAll()
                .stream().filter(x -> x.getName().equalsIgnoreCase(name))
                .filter(x->x.getPrice()<price)
                .collect(Collectors.toList());
        model.addAttribute("GetByNameAndPrice", getByNameAndPrice);

        return "announcement/byNameAndPrice";
    }

    @GetMapping("/getByPriceLowerThan")
    public String getAnnouncementByPrice(@RequestParam Integer price, Model model) {
        List<Announcement> getByPrice = announcementService.getAll()
                .stream().filter(x->x.getPrice()<price).collect(Collectors.toList());
        model.addAttribute("GetByPrice", getByPrice);

        return "announcement/byPrice";
    }

    @PatchMapping("/{id}")
    public String update(@RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "price", required = false) Integer price,
                         @RequestParam(value = "startDate", required = false) Date startDate,
                         @RequestParam(value = "soldDate", required = false) Date soldDate,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "vip", required = false) Boolean vip,
                         @RequestParam(value = "sold", required = false) Boolean sold,
                         Model model){
        return "announcement/update";
    }
}
