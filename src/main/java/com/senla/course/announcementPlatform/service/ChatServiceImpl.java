package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.ChatDao;
import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.service.interfaces.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatDao chatDao;

    public ChatServiceImpl(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Override
    public void create(Chat chat) {
        chatDao.create(chat);
    }

    @Override
    public void delete(Integer id) {
            chatDao.delete(getById(id));
    }

    @Override
    public void update(Chat chat) {
        chatDao.update(chat);
    }

    @Override
    public Chat getById(Integer id) {
        return chatDao.getById(id);
    }

    @Override
    public List<Chat> getAll() {
        return chatDao.getAll();
    }

    @Override
    public List<Chat> getChatByUser(String userSender, String userResipient) {
        List<Chat> allChat = chatDao.getAll();

        List<Chat> list = allChat.stream()
                .filter(x -> x.getChatSender().getLogin().equalsIgnoreCase(userSender)
                        || x.getChatSender().getLogin().equalsIgnoreCase(userResipient))
                .filter(x -> x.getChatRecipient().getLogin().equalsIgnoreCase(userResipient)
                        || x.getChatRecipient().getLogin().equalsIgnoreCase(userSender))
                .collect(Collectors.toList());
        return list;
    }
}
