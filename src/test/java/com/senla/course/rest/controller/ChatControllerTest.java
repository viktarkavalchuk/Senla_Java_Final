package com.senla.course.rest.controller;


import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.builder.ChatBuilder;
import com.senla.course.rest.builder.UserBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class ChatControllerTest extends BasicControllerTest {

    @MockBean
    private ChatServiceImpl chatService;
    @MockBean
    private UserServiceImpl userService;

    @Test
    public void givenNoToken_whenGetAllSequreRequest_thenForbidden() throws Exception {
        mvc.perform(get("/chat/getMessage?Recipient=User2"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllMessagesToUser1() throws Exception {
        String accessToken = obtainAccessToken("User1");
        given(userService.getByLogin(any(String.class))).willReturn(UserBuilder.userBuilderUser1());
        given(chatService.getChatByUser(any(String.class),any(String.class))).willReturn(ChatBuilder.chatsBuilder());

        mvc.perform(
                get("/chat/getMessage?Recipient=User2")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                MockMvcResultMatchers.jsonPath("$[0].message").value("Message");
    }

    @Test
    public void createChatMessageToUser2() throws Exception {
        given(userService.getByLogin("User1")).willReturn(UserBuilder.userBuilderUser1());
        given(userService.getByLogin("User2")).willReturn(UserBuilder.userBuilderUser2());

        String accessToken = obtainAccessToken("User1");

        mvc.perform(
                post("/chat?recipient=User2&message=Hello World")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        MockMvcResultMatchers.jsonPath("$.message").value("Hello World");
    }
}