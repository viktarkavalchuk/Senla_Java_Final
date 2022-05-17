package com.senla.course.announcementPlatform.service.interfaces;

import com.senla.course.announcementPlatform.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void create(User user);
    void delete(Integer id);
    void update(User user);
    User getById(Integer id);

    List<User> getAll();

}
