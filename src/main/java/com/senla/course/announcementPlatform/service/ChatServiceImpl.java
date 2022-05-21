package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.ChatDao;
import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.service.interfaces.ChatService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChatServiceImpl implements ChatService {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private ChatDao chatDao;

    @Override
    public void create(Chat chat) {
        chatDao.create(chat);
    }

    @Override
    public void delete(Integer id) {
        if (chatDao.getById(id) != null) {
            chatDao.delete(getById(id));
        } else {
            logger.error("Object does not exist");
        }
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
