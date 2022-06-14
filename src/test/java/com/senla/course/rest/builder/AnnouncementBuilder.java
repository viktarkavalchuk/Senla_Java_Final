package com.senla.course.rest.builder;

import com.senla.course.announcementPlatform.model.Announcement;

import java.util.Date;

import static com.senla.course.rest.builder.CommentBuilder.commentBuilder;
import static com.senla.course.rest.builder.UserBuilder.userBuilderUser1;

public class AnnouncementBuilder {

    public static Announcement announcementBuilder() {

        Announcement announcement = new Announcement();
        announcement.setId(1);
        announcement.setName("Announcement");
        announcement.setUser(userBuilderUser1());
        announcement.setRating(5.0);
        announcement.setVip(false);
        announcement.setDescription("Description");
        announcement.setStartDate(new Date());
        announcement.setPrice(100);

        return announcement;
    }
}
