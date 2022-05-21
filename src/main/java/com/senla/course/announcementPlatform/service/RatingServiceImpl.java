package com.senla.course.announcementPlatform.service;

import com.senla.course.announcementPlatform.dao.RatingDao;
import com.senla.course.announcementPlatform.model.Rating;
import com.senla.course.announcementPlatform.service.interfaces.RatingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private RatingDao ratingDao;

    @Override
    public void create(Rating rating) {
        ratingDao.create(rating);
    }

    @Override
    public void delete(Integer id) {
        if (ratingDao.getById(id) != null) {
            ratingDao.delete(getById(id));
        } else {
            logger.error("Object does not exist");
        }
    }

    @Override
    public void update(Rating rating) {
        ratingDao.update(rating);
    }

    @Override
    public Rating getById(Integer id) {
        return ratingDao.getById(id);
    }

    @Override
    public List<Rating> getAll() {
        return ratingDao.getAll();
    }
}
