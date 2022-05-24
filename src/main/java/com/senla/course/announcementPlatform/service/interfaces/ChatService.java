package com.senla.course.announcementPlatform.service.interfaces;

import com.senla.course.announcementPlatform.model.Chat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {
    void create(Chat chat);
    void delete(Integer id);
    void update(Chat chat);
    Chat getById(Integer id);

    List<Chat> getAll();
    List<Chat> getChatByUser(String userSender, String userResipient);
}
