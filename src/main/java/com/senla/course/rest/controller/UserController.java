package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.UserDto;
import com.senla.course.security.dao.RoleDao;
import com.senla.course.security.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger();

    private final AnnouncementServiceImpl announcementService;
    private final PasswordEncoder encoder;
    private final UserServiceImpl userService;
    private final RoleDao roleDao;
    private final BasicConverter<User, UserDto> converter;

    public UserController(AnnouncementServiceImpl announcementService, PasswordEncoder encoder, UserServiceImpl userService, RoleDao roleDao, BasicConverter<User, UserDto> converter) {
        this.announcementService = announcementService;
        this.encoder = encoder;
        this.userService = userService;
        this.roleDao = roleDao;
        this.converter = converter;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public String registration(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("telephone_Number") String telephoneNumber,
                               @RequestParam("login") String login,
                               @RequestParam("password") String password,
                               @RequestParam("ROLES") List<Integer> rolesId,
                               Model model){

        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setTelephoneNumber(telephoneNumber);
        user.setLogin(login);
        user.setPassword(encoder.encode(password));

        Set<Role> roleSet = new HashSet<>();

        for (Integer role:  rolesId) {
            roleSet.add(roleDao.getById(role));
        }
        user.setRoles(roleSet);

        userService.create(user);
        model.addAttribute(user);
        return "user/create";
    }

    @Secured("ROLE_USER")
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "telephone_Number", required = false) String telephoneNumber,
                         @RequestParam(value = "login", required = false) String login,
                         @RequestParam(value = "password", required = false) String password,
                         Model model){

        User user = userService.getById(id);
        if (name != null) {
            user.setUserName(name);
        }
        if (email !=  null) {
            user.setEmail(email);
        }
        if (telephoneNumber != null) {
            user.setTelephoneNumber(telephoneNumber);
        }
        if (login != null) {
            user.setLogin(login);
        }
        if (password != null) {
            user.setPassword(encoder.encode(password));
        }

        userService.update(user);
        model.addAttribute(user);
        return "user/update";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "user/ok";
    }

    @Secured(("ROLE_USER"))
    @GetMapping("/getId")
    public ResponseEntity<?> getAllUsers() {

        User user = userService.getById(1);
        System.out.println(user.getUserName());
        return new ResponseEntity<>(converter.convertToDto(user, UserDto.class), HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.OK);
    }

}
