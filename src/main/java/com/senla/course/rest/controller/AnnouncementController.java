package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.AnnouncementDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Announcement Controller", description = "Private ads, creation, editing, deletion")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping(value = "/announcement")
public class AnnouncementController {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementController.class);
    private final AnnouncementServiceImpl announcementService;
    private final UserServiceImpl userService;
    private final BasicConverter<Announcement, AnnouncementDto> converter;
    final HttpHeaders httpHeaders= new HttpHeaders();

    public AnnouncementController(AnnouncementServiceImpl announcementService, UserServiceImpl userService, BasicConverter<Announcement, AnnouncementDto> converter) {
        this.announcementService = announcementService;
        this.userService = userService;
        this.converter = converter;
    }

    @Operation(
            summary = "Get all announcements",
            description = "Allows you to get a list of all available ads"
    )
    @GetMapping("/getAll")
    public ResponseEntity<?> getAnnouncement() {
        List<Announcement> vip = announcementService.getVip();
        List<Announcement> notVip = announcementService.getNotVip();
        Collections.sort(vip, Announcement.COMPARE_BY_RATING.reversed());
        Collections.sort(notVip, Announcement.COMPARE_BY_RATING.reversed());
        vip.addAll(notVip);

        return new ResponseEntity<>(vip.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class)), HttpStatus.OK);
    }
    @Operation(
            summary = "Get announcements, filter by name",
            description = "Allows you to get a list of all available ads, filter by the name of the ad"
    )
    @GetMapping("/getByName")
    public ResponseEntity<?> getAnnouncementByName(@RequestParam String name) {
        List<Announcement> getByName = announcementService.getAll()
                .stream().filter(x -> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList());

        return new ResponseEntity<>(getByName.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get announcements, filter by name and price",
            description = "Allows you to get a list of all available ads, filter by ad name and price"
    )
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

    @Operation(
            summary = "Get announcements, filter by price",
            description = "Allows you to get a list of all available ads, filter by price"
    )
    @GetMapping("/getByPriceLowerThan")
    public ResponseEntity<?> getAnnouncementByPrice(@RequestParam Integer price) {
        List<Announcement> getByPrice = announcementService.getAll()
                .stream().filter(x -> x.getPrice() < price).collect(Collectors.toList());

        return new ResponseEntity<>(getByPrice.stream().map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all sold ads",
            description = "Allows you to get a list of all closed ads"
    )
    @GetMapping("/getSalesHistory")
    public ResponseEntity<?> getAllSalesHistory() {
        List<Announcement> getSalesHistoryByUser = announcementService.getClosedAnnouncements();

        return new ResponseEntity<>(getSalesHistoryByUser.stream()
                .map(d -> converter.convertToDto(d, AnnouncementDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Operation(
            summary = "Get sold ads by Id",
            description = "Get a list of closed ads by User Id"
    )
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

    @Operation(
            summary = "Set announcement as VIP or not VIP (only for Admin)",
            description = "A user with the ADMIN role can set VIP status"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @Operation(summary = "Update announcement")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAnnouncement(@PathVariable("id") int id,
                                                @RequestParam(value = "name", required = false) String name,
                                                @RequestParam(value = "price", required = false) Integer price,
                                                @RequestParam(value = "description", required = false) String description,
                                                @RequestParam(value = "sold", required = false) Boolean sold) {

        logger.info("UPDATE: try to update ID Announcement: " + id);
        try {
            User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            Announcement announcement = announcementService.getById(id);

            if (announcement.getUser().getLogin().equalsIgnoreCase(userRequest.getLogin())) {
                if (name != null) {
                    announcement.setName(name);
                }
                if (price != null) {
                    announcement.setPrice(price);
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

    @Operation(
            summary = "Create announcement",
            description = "Allows you to create an ad, you need to specify the name of the ad, price and description"
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> createAnnouncement(@RequestParam(value = "name") String name,
                                                @RequestParam(value = "price") Integer price,
                                                @RequestParam(value = "description", required = false) String description) {

        Announcement announcement = new Announcement();
        User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        announcement.setName(name);
        announcement.setPrice(price);
        announcement.setStartDate(new Date());
        announcement.setDescription(description);
        announcement.setUser(userRequest);
        announcement.setVip(false);
        announcement.setRating(5.0);

        announcementService.create(announcement);

        return new ResponseEntity<>(converter.convertToDto(announcement, AnnouncementDto.class), HttpStatus.OK);
    }

    @Operation(summary = "Delete announcement (only for Admin)")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
