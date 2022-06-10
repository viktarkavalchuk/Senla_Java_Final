package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.AnnouncementDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;


@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
    private final AnnouncementServiceImpl announcementService;
    private final UserServiceImpl userService;
    private final BasicConverter<Announcement, AnnouncementDto> converter;

    public AnnouncementController(AnnouncementServiceImpl announcementService, UserServiceImpl userService, BasicConverter<Announcement, AnnouncementDto> converter) {
        this.announcementService = announcementService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAnnouncement() {
        List<Announcement> vip = announcementService.getVip();
        List<Announcement> notVip = announcementService.getNotVip();
        Collections.sort(vip, Announcement.COMPARE_BY_RATING.reversed());
        Collections.sort(notVip, Announcement.COMPARE_BY_RATING.reversed());
        vip.addAll(notVip);

        List<AnnouncementDto> dto = vip.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList());


        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getAnnouncementByName(@RequestParam String name) {
        List<Announcement> getByName = announcementService.getAll()
                .stream().filter(x -> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList());

        return new ResponseEntity<>(getByName.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/getByNameAndPrice")
    public ResponseEntity<?> getAnnouncementByNameAndPrice(@RequestParam String name, Integer price) {
        List<Announcement> getByNameAndPrice = announcementService.getAll()
                .stream().filter(x -> x.getName().equalsIgnoreCase(name))
                .filter(x -> x.getPrice() < price)
                .collect(Collectors.toList());

        return new ResponseEntity<>(getByNameAndPrice.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/getByPriceLowerThan")
    public ResponseEntity<?> getAnnouncementByPrice(@RequestParam Integer price) {
        List<Announcement> getByPrice = announcementService.getAll()
                .stream().filter(x -> x.getPrice() < price).collect(Collectors.toList());

        return new ResponseEntity<>(getByPrice.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/getSalesHistory")
    public ResponseEntity<?> getAllSalesHistory() {
        List<Announcement> getSalesHistoryByUser = announcementService.getClosedAnnouncements();

        return new ResponseEntity<>(getSalesHistoryByUser.stream()
                .map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getSalesHistory/{id}")
    public ResponseEntity<?> getSalesHistory(@PathVariable("id") int id) {
        User user = userService.getById(id);
        List<Announcement> getSalesHistoryByUser = announcementService.getClosedAnnouncements()
                .stream()
                .filter(x -> x.getUser().getLogin().equals(user.getLogin()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(getSalesHistoryByUser.stream()
                .map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PatchMapping("/vip/{id}")
    public ResponseEntity<?> setVip(@PathVariable("id") int id,
                                    @RequestParam(value = "vip") Boolean vip) {
        logger.info("UPDATE: try to update ID Announcement: " + id);
        try {
            Announcement announcement = announcementService.getById(id);
            announcement.setVip(vip);
            announcementService.update(announcement);
            logger.info("UPDATE: update ID Announcement: " + id);
            return new ResponseEntity<>("UPDATE: update ID Announcement: " + id, HttpStatus.OK);
        } catch (NoResultException e) {
            logger.error("UPDATE: Can't update ad ID. It doesn't exist: " + id, e);
            return new ResponseEntity<>("UPDATE: Can't update ad ID. It doesn't exist: " + id, HttpStatus.OK);
        }
    }

    @Secured("ROLE_USER")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAnnouncement(@PathVariable("id") int id,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "price", required = false) Integer price,
                                                @RequestParam(value = "description", required = false) String description,
                                                @RequestParam(value = "sold", required = false) Boolean sold) {

        logger.info("UPDATE: try to update ID Announcement: " + id);
        try {
            User user = userService.getById(idUserLogin);
            Announcement announcement = announcementService.getById(id);

            if (announcement.getUser().getLogin().equalsIgnoreCase(user.getLogin())) {
                if (name != null) {
                    announcement.setName(name);
                }
                if (description != null) {
                    announcement.setDescription(description);
                }
                if (sold != null && sold == true) {
                    announcement.setSold(sold);
                    announcement.setEndDate(new Date());
                } else if (sold != null && sold == false) {
                    announcement.setSold(sold);
                    announcement.setEndDate(null);
                }
                announcementService.update(announcement);
                logger.info("UPDATE: update succsessfully: " + id);
                return new ResponseEntity<>(converter.convertToDto(announcement, AnnouncementDto.class), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("UPDATE: This user can`t change this announcement", HttpStatus.OK);
            }
        } catch (NoResultException e) {
            logger.error("UPDATE: This Announcement doesn`t exist, ID Announcement: " + id);
            return new ResponseEntity<>("UPDATE: This Announcement doesn`t exist, ID Announcement: " + id, HttpStatus.OK);
        }
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<?> createAnnouncement(@RequestParam(value = "name") String name,
                                                @RequestParam(value = "price") Integer price,
                                                @RequestParam(value = "description", required = false) String description) {

        Announcement announcement = new Announcement();
        User user = userService.getById(idUserLogin);
        announcement.setName(name);
        announcement.setPrice(price);
        announcement.setStartDate(new Date());
        announcement.setDescription(description);
        announcement.setUser(user);
        announcement.setVip(false);
        announcement.setRating(5.0);

        announcementService.create(announcement);

        return new ResponseEntity<>(converter.convertToDto(announcement, AnnouncementDto.class), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {

        logger.info("DELETE: try to delete ID Announcement: " + id);
        try {
            announcementService.delete(id);
            logger.info("Deleted successfully, ID Announcement: " + id);
        } catch (NoResultException e) {
            logger.error("This Announcement doesn`t exist, ID Announcement: " + id, e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
