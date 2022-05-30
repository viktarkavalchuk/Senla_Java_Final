package com.senla.course.rest.dto;

import com.senla.course.announcementPlatform.model.User;

public class ChatDto {
    private int id;
    private String message;
    private User chatSender;
    private User chatRecipient;

    public ChatDto() {
    }

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
