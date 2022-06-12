package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.rest.builder.AnnouncementBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class AnnouncementControllerTest extends BasicControllerTest{

    @MockBean
    private AnnouncementServiceImpl announcementService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void announcementGetAll() throws Exception {
        String accessToken = obtainAccessToken("User1");

        Announcement announcement = AnnouncementBuilder.announcementBuilder();

        List<Announcement> allAnnouncement = Arrays.asList(announcement);
        given(announcementService.getVip()).willReturn(allAnnouncement);

        mvc.perform(get("/announcement/getAll")
                .header("Authorization", "Bearer " + accessToken))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        MockMvcResultMatchers.jsonPath("$[0].id").value(announcement.getId()));
    }

    @Test
    public void givenNoToken_whenDeleteSecureRequest_thenForbidden() throws Exception {
        mvc.perform(get("/announcement/getAll"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteAnnouncement_whenDeleteSecureRequest_thenForbidden() throws Exception {
        String accessToken = obtainAccessToken("User1");
        doNothing().when(announcementService).delete(anyInt());

        mvc.perform(delete("/announcement/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isForbidden());
        verifyNoMoreInteractions(announcementService);
    }
    @Test
    public void deleteAnnouncement_whenDeleteSecureRequest_thenOk() throws Exception {
        String accessToken = obtainAccessToken("Admin");
        doNothing().when(announcementService).delete(anyInt());

        mvc.perform(delete("/announcement/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());

        verify(announcementService).delete(1);
        verifyNoMoreInteractions(announcementService);
    }
}
