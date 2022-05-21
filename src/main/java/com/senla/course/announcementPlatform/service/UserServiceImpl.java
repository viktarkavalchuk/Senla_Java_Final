package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.UserDao;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.interfaces.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private UserDao userDao;

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void delete(Integer id) {
        if (userDao.getById(id) != null) {
            userDao.delete(getById(id));
        } else {
            logger.error("Object does not exist");
        }
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }
}
