package com.senla.course.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;


@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
    private static final Logger logger = LogManager.getLogger();
    private final AnnouncementServiceImpl announcementService;
    private final UserServiceImpl userService;

    public AnnouncementController(AnnouncementServiceImpl announcementService, UserServiceImpl userService) {
        this.announcementService = announcementService;
        this.userService = userService;
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

    @Secured("ROLE_USER")
    @PatchMapping("/{id}")
    public String updateAnnouncement(@PathVariable("id") int id,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "price", required = false) Integer price,
                         @RequestParam(value = "startDate", required = false) Date startDate,
                         @RequestParam(value = "soldDate", required = false) Date soldDate,
                         @RequestParam(value = "description", required = false) String description,
                         @RequestParam(value = "vip", required = false) Boolean vip,
                         @RequestParam(value = "sold", required = false) Boolean sold,
                         Model model){

        User user = userService.getById(idUserLogin);
        Announcement announcement = announcementService.getById(id);
        String address = null;

        if (announcement.getUser().getLogin().equalsIgnoreCase(user.getLogin())){
            if (name != null) {
                announcement.setName(name);
            }
            if (price !=  null) {
                announcement.setStartDate(startDate);
            }
            if (soldDate != null) {
                announcement.setEndDate(soldDate);
            }
            if (description != null) {
                announcement.setDescription(description);
            }
            if (vip != null) {
                announcement.setVip(vip);
            }
            if (sold != null) {
                announcement.setSold(sold);
            }
            announcementService.update(announcement);
            model.addAttribute(announcement);
            address = "announcement/update";
        } else {
            model.addAttribute("Error", "this user can`t change this announcement");
            address = "announcement/error";
        }
        return address;
    }

    @Secured("ROLE_USER")
    @PostMapping
    public String createAnnouncement(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "price", required = false) Integer price,
                                     @RequestParam(value = "startDate", required = false) Date startDate,
                                     @RequestParam(value = "description", required = false) String description,
                                     Model model){

        Announcement announcement = new Announcement();
        User user = userService.getById(idUserLogin);
        announcement.setName(name);
        announcement.setPrice(price);
        announcement.setStartDate(startDate);
        announcement.setDescription(description);
        announcement.setUser(user);
        announcement.setVip(false);
        announcement.setRating(5.0);

        announcementService.create(announcement);
        model.addAttribute(announcement);
        return "announcement/create";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        announcementService.delete(id);
        return "announcement/ok";
    }
}
