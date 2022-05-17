package com.senla.course.announcementPlatform.service.interfaces;

import com.senla.course.announcementPlatform.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    void create(Comment comment);
    void delete(Integer id);
    void update(Comment comment);
    Comment getById(Integer id);

    List<Comment> getAll();

}
