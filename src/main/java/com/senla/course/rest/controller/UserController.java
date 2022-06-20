package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.UserDto;
import com.senla.course.security.dao.RoleDao;
import com.senla.course.security.model.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users
                .stream().map(d -> converter.convertToDto(d, UserDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ResponseEntity<?> update(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "email", required = false) String email,
                                    @RequestParam(value = "telephone_Number", required = false) String telephoneNumber,
                                    @RequestParam(value = "login", required = false) String login,
                                    @RequestParam(value = "password", required = false) String password) {

        User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        if (userRequest != null) {
            if (name != null) {
                userRequest.setUserName(name);
            }
            if (email != null) {
                userRequest.setEmail(email);
            }
            if (telephoneNumber != null) {
                userRequest.setTelephoneNumber(telephoneNumber);
            }
            if (login != null) {
                userRequest.setLogin(login);
            }
            if (password != null) {
                userRequest.setPassword(encoder.encode(password));
            }
            userService.update(userRequest);
            logger.info("User updated successfully, id User: " + userRequest);
            return new ResponseEntity<>(converter.convertToDto(userRequest, UserDto.class), HttpStatus.OK);
        } else {
            logger.error("User update error, id user does not exist: " + userRequest);
            return new ResponseEntity<>("User update error id, user: " + userRequest, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
