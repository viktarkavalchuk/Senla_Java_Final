package com.senla.course.rest.dto;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.User;

public class CommentDto {

    private int id;
    private String bodyComment;
    private User userSender;
    private Announcement announcement;

    public CommentDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBodyComment() {
        return bodyComment;
    }

    public void setBodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
    }

    public String getUserSender() {
        return userSender.getUserName();
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public String getAnnouncement() {
        return announcement.getName();
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
