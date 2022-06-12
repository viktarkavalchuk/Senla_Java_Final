package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.builder.UserBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class ChatControllerTest extends BasicControllerTest {

    @MockBean
    private ChatServiceImpl chatService;
    @MockBean
    private UserServiceImpl userService;


    @Test
    void getAll() {
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void createChatMessage() throws Exception {

       given(userService.getByLogin("Login")).willReturn(UserBuilder.userBuilder());
        mvc.perform(
                post("/chat")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
}