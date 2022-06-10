package com.senla.course.announcementPlatform.model;

import com.senla.course.security.model.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user", schema = "private_announcements")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_User")
    private int id;
    @Column(name = "name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone_Number")
    private String telephoneNumber;
    @OneToMany(targetEntity = Announcement.class, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Announcement> announcements;

    @OneToMany(targetEntity = Rating.class,
            mappedBy = "toUser",
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            orphanRemoval = true)
    private Set<Rating> ratings;

    @OneToMany(targetEntity = Chat.class,
            mappedBy = "chatRecipient",
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            orphanRemoval = true)
    private Set<Chat> chatRecipients;
    @OneToMany(targetEntity = Chat.class,
            mappedBy = "chatSender",
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE,
            orphanRemoval = true)
    private Set<Chat> chatSenders;

    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "role"))
    private Set<Role> roles;

    public User() {
    }

    public User(String login, String password, Set<Role> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public User(String userName, String email, String telephoneNumber, String login) {
        this.userName = userName;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.login = login;
    }

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<Chat> getChatRecipients() {
        return chatRecipients;
    }

    public void setChatRecipients(Set<Chat> chatRecipients) {
        this.chatRecipients = chatRecipients;
    }

    public Set<Chat> getChatSenders() {
        return chatSenders;
    }

    public void setChatSenders(Set<Chat> chatSenders) {
        this.chatSenders = chatSenders;
    }
}
