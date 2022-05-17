package com.senla.course.announcementPlatform.service.interfaces;

import com.senla.course.announcementPlatform.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    void create(Rating rating);
    void delete(Integer id);
    void update(Rating rating);
    Rating getById(Integer id);

    List<Rating> getAll();
}
