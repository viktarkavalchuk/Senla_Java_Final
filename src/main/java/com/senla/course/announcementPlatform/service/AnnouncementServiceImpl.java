package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.AnnouncementDao;
import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.service.interfaces.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    AnnouncementDao announcementDao;

    @Override
    public void create(Announcement announcement) {
        announcementDao.create(announcement);
    }

    @Override
    public void delete(Integer id) {
        announcementDao.delete(getById(id));
    }

    @Override
    public void update(Announcement announcement) {
        announcementDao.update(announcement);
    }

    @Override
    public Announcement getById(Integer id) {
        return announcementDao.getById(id);
    }

    @Override
    public List<Announcement> getAll() {
        return announcementDao.getAll();
    }
}
