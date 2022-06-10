package com.senla.course.rest.controller;


import com.senla.course.announcementPlatform.model.Announcement;
import com.senla.course.announcementPlatform.service.AnnouncementServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.builder.AnnouncementBuilder;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.AnnouncementDto;
import com.senla.course.security.service.UserDetailService;
import com.senla.course.security.utils.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static javax.management.Query.value;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.isToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockitoSession;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {AnnouncementController.class, BasicConverter.class, JwtUtil.class})
public class AnnouncementControllerTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private AnnouncementServiceImpl announcementService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private UserDetailService user;
    @Autowired
    private BasicConverter<Announcement, AnnouncementDto> converter;

    @Autowired
    private JwtUtil jwtUtil;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .dispatchOptions(true)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void givenNoToken_whenGetOneSecureRequest_thenUnauthorized() throws Exception {
        mvc.perform(get("/announcement/getAll"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER")
    public void getAllAnnouncement() throws Exception {

        String accessToken = jwtUtil.generateToken("Admin");
        Announcement announcement = AnnouncementBuilder.announcementBuilder();

        List<Announcement> allAnnouncement = Arrays.asList(announcement);
        given(announcementService.getVip()).willReturn(allAnnouncement);

        mvc.perform(
                get("/announcement/getAll")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$name", is(announcement.getName())));
    }


    @Test
    @WithMockUser(roles = "USER")
    public void creating() throws Exception {

        Announcement announcement = AnnouncementBuilder.announcementBuilder();

        List<Announcement> allAnnouncement = Arrays.asList(announcement);
        given(announcementService.getVip()).willReturn(allAnnouncement);

        String accessToken = jwtUtil.generateToken("User");

        // when
        MockHttpServletRequestBuilder response = MockMvcRequestBuilders
                .get("/announcement/getAll")
                        .accept(MediaType.APPLICATION_JSON);
        ResultActions result = mvc.perform((response));
        // then




//        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
//                .get("/announcement/getAll")
//                .header("Authorization", "Bearer " + accessToken);
//        ResultActions result = mvc.perform(request);

//        mvc.perform(get("/announcement/getAll"));
//        assertThat(response.getContentAsString()).isEmpty();
    }

    @Test
    @WithMockUser(roles="USER")
    public void creatingAnnouncement() throws Exception {

        Announcement announcement = AnnouncementBuilder.announcementBuilder();
        List<Announcement> allAnnouncement = Arrays.asList(announcement);
        doNothing().when(announcementService).create(announcement);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name",  "Name");
        requestParams.add("price", "100");
        requestParams.add("description", "description");

        mvc.perform(
                post("/announcement/0")
                .params(requestParams))
                .andExpect(status().isOk());
    }
}
