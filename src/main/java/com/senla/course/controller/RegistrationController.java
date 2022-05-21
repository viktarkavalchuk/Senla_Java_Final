package com.senla.course.controller;

import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.security.dao.RoleDao;
import com.senla.course.security.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger logger = LogManager.getLogger();

    private final PasswordEncoder encoder;
    private final UserServiceImpl userService;
    private final RoleDao roleDao;

    public RegistrationController(PasswordEncoder encoder, UserServiceImpl userService, RoleDao roleDao) {
        this.encoder = encoder;
        this.userService = userService;
        this.roleDao = roleDao;
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
        return "registration/index";
    }

    @Secured("ROLE_USER")
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @RequestParam(value = "name", required = false) String name,
                         @RequestParam(value = "email", required = false) String email,
                         @RequestParam(value = "telephone_Number", required = false) String telephoneNumber,
                         @RequestParam(value = "login", required = false) String login,
                         @RequestParam(value = "password", required = false) String password,
                         @RequestParam(value = "ROLES", required = false) List<Integer> rolesId,
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

        if (rolesId != null) {
            Set<Role> roleSet = new HashSet<>();
            for (Integer role : rolesId) {
                roleSet.add(roleDao.getById(role));
            }
            user.setRoles(roleSet);
        }
        userService.update(user);
        model.addAttribute(user);
        return "registration/update";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "registration/ok";
    }
}
