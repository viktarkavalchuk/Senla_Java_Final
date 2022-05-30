package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.AnnouncementDao;
import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.service.interfaces.AnnouncementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private static final Logger logger = LogManager.getLogger();
    private final AnnouncementDao announcementDao;

    public AnnouncementServiceImpl(AnnouncementDao announcementDao) {
        this.announcementDao = announcementDao;
    }

    @Override
    public void create(Announcement announcement) {
        announcementDao.create(announcement);
    }

    @Override
    public void delete(Integer id) {
        Announcement announcement = null;
        if (announcementDao.getById(id) != null) {
            announcementDao.delete(getById(id));
        } else {
            logger.error("Object does not exist");
        }
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

    @Override
    public List<Announcement> getVip() {
        return announcementDao.getVipAnnouncements();
    }

    @Override
    public List<Announcement> getNotVip() {
        return announcementDao.getNotVipAnnouncements();
    }

    @Override
    public List<Announcement> getClosedAnnouncements() {
        return announcementDao.getClosedAnnouncements();
    }
}
