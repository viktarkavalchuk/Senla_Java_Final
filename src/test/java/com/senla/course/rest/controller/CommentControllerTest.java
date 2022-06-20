package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.model.Comment;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.CommentServiceImpl;
import com.senla.course.rest.builder.AnnouncementBuilder;
import com.senla.course.rest.builder.CommentBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class CommentControllerTest extends BasicControllerTest {

    @MockBean
    private CommentServiceImpl commentService;
    @MockBean
    private AnnouncementServiceImpl announcementService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsers_whenAuthorizationIsAdmin_thenOk() throws Exception {
        String accessToken = obtainAccessToken("Admin");
        List<Comment> comments = CommentBuilder.commentBuilder();
        given(announcementService.getById(anyInt())).willReturn(AnnouncementBuilder.announcementBuilder());
        given(commentService.getByAnnouncement(any(Announcement.class))).willReturn(comments);

        mvc.perform(
                get("/comment/getCommentsByAnnouncement?announcement=1")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(comments.get(0).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void createComment_whenAuthorizationIsAdmin_thenOk() throws Exception {
        String accessToken = obtainAccessToken("User1");
        given(announcementService.getById(anyInt())).willReturn(AnnouncementBuilder.announcementBuilder());
        mvc.perform(
                post("/comment?announcementId=1&bodyComment=why is the price so high?")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userSender").value("User_1"))
                .andExpect(status().isOk());
    }
}
