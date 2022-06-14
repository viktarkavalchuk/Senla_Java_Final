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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    public void getAllMessages_whenAuthorizationIsUser1_thenOk() throws Exception {
        String accessToken = obtainAccessToken("User1");
        given(userService.getByLogin(anyString())).willReturn(UserBuilder.userBuilderUser1());
        given(chatService.getChatByUser(anyString(),anyString())).willReturn(ChatBuilder.chatsBuilder());

        mvc.perform(
                get("/chat/getMessage?Recipient=User2")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].message").value("Message"))
                    .andExpect(status().isOk());
    }

    @Test
    public void CreateChatMessage_whenAuthorizationIsUser2_thenOk() throws Exception {
        given(userService.getByLogin("User1")).willReturn(UserBuilder.userBuilderUser1());
        given(userService.getByLogin("User2")).willReturn(UserBuilder.userBuilderUser2());

        String accessToken = obtainAccessToken("User1");

        mvc.perform(
                post("/chat?recipient=User2&message=Hello World")
                    .header("Authorization", "Bearer " + accessToken)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.message").value("Hello World"))
                    .andExpect(status().isOk());
    }
}