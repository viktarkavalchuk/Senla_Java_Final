package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.Comment;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.CommentServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.CommentDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private static final Logger logger = LogManager.getLogger();
    private final AnnouncementServiceImpl announcementService;
    private final CommentServiceImpl commentService;
    private final UserServiceImpl userService;
    private final BasicConverter<Comment, CommentDto> converter;


    public CommentController(CommentServiceImpl commentService, AnnouncementServiceImpl announcementService, UserServiceImpl userService, BasicConverter<Comment, CommentDto> converter) {
        this.commentService = commentService;
        this.announcementService = announcementService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/getAllComments")
    public ResponseEntity<?> getAllComments() {

        List<Comment> comments = commentService.getAll();

        return new ResponseEntity<>(comments.stream().map(d -> converter.convertToDto(d, CommentDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/getCommentsByAnnouncement")
    public ResponseEntity<?> getCommentsByAnnouncement(@RequestParam(value = "announcement") Integer id) {
        logger.info("GET: try to get CommentsByAnnouncement, ID Announcement: " + id);
        try {
            Announcement announcement = announcementService.getById(id);
            List<Comment> comments = commentService.getByAnnouncement(announcement);
            logger.info("GET: get CommentsByAnnouncement succsessfully, ID Announcement: " + id);
            return new ResponseEntity<>(comments.stream().map(d -> converter.convertToDto(d, CommentDto.class))
                    .collect(Collectors.toList()),
                    HttpStatus.OK);
        } catch (NoResultException e) {
            logger.error("This Announcement doesn`t exist, ID Announcement: " + id);
            return new ResponseEntity<>("This Announcement doesn`t exist, ID Announcement: " + id, HttpStatus.OK);
        }
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<?> createComment(@RequestParam(value = "announcementId") Integer id,
                                           @RequestParam(value = "bodyComment") String bodyComment) {

        User user = userService.getById(idUserLogin);
        Comment comment = new Comment();
        comment.setUserSender(user);
        comment.setAnnouncement(announcementService.getById(id));
        comment.setBodyComment(bodyComment);

        commentService.create(comment);

        return new ResponseEntity<>(converter.convertToDto(comment, CommentDto.class), HttpStatus.OK);
    }

}
