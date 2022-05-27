package com.senla.course.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.Comment;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.CommentServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private static final Logger logger = LogManager.getLogger();
    private final AnnouncementServiceImpl announcementService;
    private final CommentServiceImpl commentService;
    private final UserServiceImpl userService;


    public CommentController(CommentServiceImpl commentService, AnnouncementServiceImpl announcementService, UserServiceImpl userService) {
        this.commentService = commentService;
        this.announcementService = announcementService;
        this.userService = userService;
    }

    @GetMapping("/getAllComments")
    public String getAllComments(Model model) {

        List<Comment> comments = commentService.getAll();
        model.addAttribute("Comments", comments);

        return "comment/all";
    }

    @GetMapping("/getCommentsByAnnouncement")
    public String getCommentsByAnnouncement(@RequestParam(value = "announcement") Integer id,Model model) {
        Announcement announcement = announcementService.getById(id);
        List<Comment> comments = commentService.getByAnnouncement(announcement);
        model.addAttribute("Comments", comments);

        return "comment/all";
    }

    @Secured("ROLE_USER")
    @PostMapping
    public String createComment(@RequestParam(value = "announcementId") Integer id,
                                @RequestParam(value = "bodyComment") String bodyComment,
                                Model model){

        User user = userService.getById(idUserLogin);
        Comment comment = new Comment();
        comment.setUserSender(user);
        comment.setAnnouncement(announcementService.getById(id));
        comment.setBodyComment(bodyComment);

        commentService.create(comment);
        model.addAttribute(comment);
        return "comment/create";
    }

}
