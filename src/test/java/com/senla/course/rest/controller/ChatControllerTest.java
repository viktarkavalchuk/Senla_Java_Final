package com.senla.course.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.dto.ChatDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {ChatController.class})
class ChatControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ChatServiceImpl chatService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private ChatDto chatDto;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getAll() {
    }

    @Test
    @WithMockUser(roles="ADMIN")
    void createChatMessage() throws Exception {
        User user1 = new User();
        User user2 = new User();
        Chat chat = new Chat();
        chat.setMessage("Message");
        chat.setChatSender(user1);
        chat.setChatRecipient(user2);
        mvc.perform(
                MockMvcRequestBuilders.post("/chat").content(objectMapper.writeValueAsString(chat)))
                .andExpect(status().isOk());
    }
}