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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

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

    @Secured(("ROLE_ADMIN"))
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users
                .stream().map(d -> converter.convertToDto(d, UserDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<?> registration(@RequestParam("name") String name,
                                          @RequestParam("email") String email,
                                          @RequestParam("telephone_Number") String telephoneNumber,
                                          @RequestParam("login") String login,
                                          @RequestParam("password") String password,
                                          @RequestParam("ROLES") List<Integer> rolesId) {

        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        user.setTelephoneNumber(telephoneNumber);
        user.setLogin(login);
        user.setPassword(encoder.encode(password));

        Set<Role> roleSet = new HashSet<>();

        for (Integer role : rolesId) {
            roleSet.add(roleDao.getById(role));
        }
        user.setRoles(roleSet);

        userService.create(user);

        return new ResponseEntity<>(converter.convertToDto(user, UserDto.class), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @PatchMapping
    public ResponseEntity<?> update(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "telephone_Number", required = false) String telephoneNumber,
                                    @RequestParam(value = "login", required = false) String login,
                                    @RequestParam(value = "password", required = false) String password) {
        User user = userService.getById(idUserLogin);
        if (user != null) {
            if (name != null) {
                user.setUserName(name);
            }
            if (email != null) {
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
            logger.info("User updated successfully, id User: " + idUserLogin);
            return new ResponseEntity<>(converter.convertToDto(user, UserDto.class), HttpStatus.OK);
        } else {
            logger.error("User update error, id user does not exist: " + idUserLogin);
            return new ResponseEntity<>("User update error id, user: " + idUserLogin, HttpStatus.OK);
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) throws NoResultException {

        logger.info("DELETE: delete User: " + id);
        try {
            userService.delete(id);
            logger.info("Deleted successfully, id: " + id);
            return new ResponseEntity<>("Deleted successfully, id User: " + id, HttpStatus.OK);
        } catch (NoResultException e) {
            logger.error("This User doesn`t exist, id User: " + id, e);
            return new ResponseEntity<>("This User doesn`t exist, id User: " + id, HttpStatus.OK);
        }
    }
}
