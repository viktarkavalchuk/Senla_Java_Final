package com.senla.course.announcementPlatform.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chat", schema = "private_announcements")
public class Chat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idChat")
    private int id;
    @Column(name = "message")
    private String message;
    @ManyToOne
    @JoinColumn(name = "chatSender")
    private User chatSender;
    @ManyToOne
    @JoinColumn(name = "chatRecipient")
    private User chatRecipient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getChatSender() {
        return chatSender;
    }

    public void setChatSender(User chatSender) {
        this.chatSender = chatSender;
    }

    public User getChatRecipient() {
        return chatRecipient;
    }

    public void setChatRecipient(User chatRecipient) {
        this.chatRecipient = chatRecipient;
    }
}
