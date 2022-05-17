package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.ChatDao;
import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.service.interfaces.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatDao chatDao;

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
}
