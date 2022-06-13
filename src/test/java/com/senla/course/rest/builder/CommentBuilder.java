package com.senla.course.rest.builder;

import com.senla.course.announcementPlatform.model.Comment;

import java.util.ArrayList;
import java.util.List;

import static com.senla.course.rest.builder.UserBuilder.userBuilderUser1;

public class CommentBuilder {

    public static List<Comment> commentBuilder() {

        Comment comment = new Comment();
        comment.setId(1);
        comment.setBodyComment("Comment_1");
        comment.setUserSender(userBuilderUser1());
        List<Comment> list = new ArrayList<>();
        list.add(comment);

        return list;
    }
}
