package com.senla.course.rest.dto;

import com.senla.course.announcementPlatform.model.User;

public class ChatDto {

    private String message;
    private User chatSender;
    private User chatRecipient;

    public ChatDto() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatSender() {
        return chatSender.getLogin();
    }

    public void setChatSender(User chatSender) {
        this.chatSender = chatSender;
    }

    public String getChatRecipient() {
        return chatRecipient.getLogin();
    }

    public void setChatRecipient(User chatRecipient) {
        this.chatRecipient = chatRecipient;
    }
}
