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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class AnnouncementControllerTest extends BasicControllerTest {

    @MockBean
    private AnnouncementServiceImpl announcementService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllAnnouncements_whenAuthorizationIsUser1_thenOk() throws Exception {
        String accessToken = obtainAccessToken("User1");

        Announcement announcement = AnnouncementBuilder.announcementBuilder();

        List<Announcement> allAnnouncement = Arrays.asList(announcement);
        given(announcementService.getVip()).willReturn(allAnnouncement);

        mvc.perform(get("/announcement/getAll")
                .header("Authorization", "Bearer " + accessToken))
                .andExpectAll(status().isOk(),
                    content().contentType(MediaType.APPLICATION_JSON),
                    jsonPath("$[0].id").value(announcement.getId()));
    }

    @Test
    public void createAnnouncement_whenAuthorizationIsUser1_thenOk() throws Exception {
        String accessToken = obtainAccessToken("User1");

        mvc.perform(
                post("/announcement/?name=Telephone Nokia 4110&price=75&description=Almost like new")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price").value("75"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateAnnouncement_whenAuthorizationIsUser1_thenOk() throws Exception {
        String accessToken = obtainAccessToken("User1");
        Announcement announcement = AnnouncementBuilder.announcementBuilder();
        given(announcementService.getById(anyInt())).willReturn(announcement);

        mvc.perform(
                patch("/announcement/8?price=10&description=Cost reduced")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.price").value(10))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteAnnouncement_whenAuthorizationIsUser1_thenForbidden() throws Exception {
        String accessToken = obtainAccessToken("User1");
        doNothing().when(announcementService).delete(anyInt());

        mvc.perform(delete("/announcement/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isForbidden());
        verifyNoMoreInteractions(announcementService);
    }

    @Test
    public void deleteAnnouncement_whenAuthorizationIsAdmin_thenOk() throws Exception {
        String accessToken = obtainAccessToken("Admin");
        doNothing().when(announcementService).delete(anyInt());

        mvc.perform(delete("/announcement/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
        verify(announcementService).delete(1);
        verifyNoMoreInteractions(announcementService);
    }
}
