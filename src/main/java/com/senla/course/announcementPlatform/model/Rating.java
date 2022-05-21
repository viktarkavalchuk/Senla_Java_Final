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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "toUser")
    private User toUser;

    @Column(name = "fromUser")
    private Integer fromUser;
    @Column(name = "rating")
    private Integer rating;

    public Rating() {
    }

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

    public Integer getFromUser() {
        return fromUser;
    }

    public void setFromUser(Integer fromUser) {
        this.fromUser = fromUser;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
