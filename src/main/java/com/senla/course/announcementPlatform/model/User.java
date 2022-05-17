package com.senla.course.announcementPlatform.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user", schema = "private_announcements")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_User")
    private int id;
    @Column(name = "name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone_Number")
    private String telephoneNumber;
    @OneToMany(targetEntity = Announcement.class, mappedBy = "user", fetch=FetchType.EAGER)
    private List<Announcement> announcements;
//    @OneToMany(targetEntity = Rating.class, mappedBy = "toUser", fetch=FetchType.EAGER)
//    private List<Rating> ratings;
//    @OneToMany(targetEntity = Chat.class, mappedBy = "chatRecipient", fetch=FetchType.EAGER)
//    private List<Chat> chats;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

//    public List<Rating> getRatings() {
//        return ratings;
//    }
//
//    public void setRatings(List<Rating> ratings) {
//        this.ratings = ratings;
//    }
//
//    public List<Chat> getChats() {
//        return chats;
//    }
//
//    public void setChats(List<Chat> chats) {
//        this.chats = chats;
//    }
}
