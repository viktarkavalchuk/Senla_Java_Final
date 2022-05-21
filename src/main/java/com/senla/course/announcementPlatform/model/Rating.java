package com.senla.course.announcementPlatform.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rating", schema = "private_announcements")
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRating")
    private int id;
    @ManyToOne
    @JoinColumn(name = "toUser")
    private User toUser;
    @ManyToOne
    @JoinColumn(name = "fromUser")
    private User fromUser;

    @Column(name = "rating")
    private Integer rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
