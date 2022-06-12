package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.Comment;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.CommentServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.CommentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> createComment(@RequestParam(value = "announcementId") Integer id,
                                           @RequestParam(value = "bodyComment") String bodyComment) {

        User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Comment comment = new Comment();
        comment.setUserSender(userRequest);
        comment.setAnnouncement(announcementService.getById(id));
        comment.setBodyComment(bodyComment);

        commentService.create(comment);

        return new ResponseEntity<>(converter.convertToDto(comment, CommentDto.class), HttpStatus.OK);
    }

}
