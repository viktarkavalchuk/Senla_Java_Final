package com.senla.course.announcementPlatform.service.interfaces;

import com.senla.course.announcementPlatform.model.Announcement;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnouncementService {
    void create(Announcement announcement);
    void delete(Integer id);
    void update(Announcement announcement);
    Announcement getById(Integer id);

    List<Announcement> getAll();
}
