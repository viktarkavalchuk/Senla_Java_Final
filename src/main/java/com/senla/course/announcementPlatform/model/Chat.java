package com.senla.course.announcementPlatform.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;

@Entity
@Table(name = "chat", schema = "private_announcements")
public class Chat implements Serializable {

    public static final Comparator<Chat> COMPARE_BY_ID = new Comparator<Chat>() {
        @Override
        public int compare(Chat lhs, Chat rhs) {
            return (int) (lhs.getId() - rhs.getId());
        }
    };
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idChat")
    private int id;
    @Column(name = "message")
    private String message;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chatSender")
    private User chatSender;
    @ManyToOne(fetch = FetchType.EAGER)
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
