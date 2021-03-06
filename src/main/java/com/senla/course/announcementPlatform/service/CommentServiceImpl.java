package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.CommentDao;
import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.Comment;
import com.senla.course.announcementPlatform.service.interfaces.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void create(Comment comment) {
        commentDao.create(comment);
    }

    @Override
    public void delete(Integer id) {
        commentDao.delete(getById(id));
    }

    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    public Comment getById(Integer id) {
        return commentDao.getById(id);
    }

    @Override
    public List<Comment> getByAnnouncement(Announcement announcement) {
        return commentDao.getByAnnouncement(announcement);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }
}
