package com.senla.course.announcementPlatform.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comment", schema = "private_announcements")
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idComment")
    private int id;
    @Column(name = "bodyComment")
    private String bodyComment;
    @OneToOne
    @JoinColumn(name = "sender")
    private User userSender;
    @ManyToOne
    @JoinColumn(name = "toAnnouncement")
    private Announcement announcement;

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

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }
}
