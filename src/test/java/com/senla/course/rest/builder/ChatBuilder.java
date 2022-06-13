package com.senla.course.rest.builder;

import com.senla.course.announcementPlatform.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class ChatBuilder {

    public static List<Chat> chatsBuilder() {

        Chat chat = new Chat();
        chat.setId(1);
        chat.setChatSender(UserBuilder.userBuilderUser1());
        chat.setChatRecipient(UserBuilder.userBuilderUser2());
        chat.setMessage("Message");
        List<Chat> list = new ArrayList<>();
        list.add(chat);

        return list;
    }
}
