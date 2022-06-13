package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.builder.UserBuilder;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
public class UserControllerTest extends BasicControllerTest {

    @MockBean
    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenNoToken_whenGetAllUsersSequreRequest_thenForbidden() throws Exception {
        mvc.perform(get("/user/getAllUsers"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getAllUsers() throws Exception {
        String accessToken = obtainAccessToken("Admin");
        List<User> users = new ArrayList<>();
        users.add(UserBuilder.userBuilderUser1());
        given(userService.getAll()).willReturn(users);

        mvc.perform(
                get("/user/getAllUsers")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MockMvcResultMatchers.jsonPath("$[0].id").value(users.get(0).getId());
    }

    @Test
    public void deleteUser_whenDeleteSecureRequest_thenOk() throws Exception {
        String accessToken = obtainAccessToken("Admin");
        doNothing().when(userService).delete(anyInt());

        mvc.perform(delete("/user/1")
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
        verify(userService).delete(1);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void createUser1_whenAuthorizationIsAdmin_thenOk() throws Exception {
        String accessToken = obtainAccessToken("Admin");

        mvc.perform(
                post("/user?name=Ivan&email=Ivan@mail.com&telephone_Number=+375331112233&login=user000&password=password&ROLES=2")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        MockMvcResultMatchers.jsonPath("$.userName").value("Ivan");
    }

    @Test
    public void createUser1_whenAuthorizationIsUser_thenForbidden() throws Exception {
        String accessToken = obtainAccessToken("User1");

        mvc.perform(
                post("/user?name=Ivan&email=Ivan@mail.com&telephone_Number=+375331112233&login=user000&password=password&ROLES=2")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isForbidden());
    }
}
