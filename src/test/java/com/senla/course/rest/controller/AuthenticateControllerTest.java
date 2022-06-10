package com.senla.course.rest.controller;

import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.security.model.AuthRequest;
import com.senla.course.security.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {})
class AuthenticateControllerTest {

    private MockMvc mvc;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .dispatchOptions(true)
                .apply(springSecurity())
                .build();
    }

    @Test
    void generateToken() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("userName",  "Name");
        requestParams.add("password", "password");

       // given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                "userName", "password"))).willReturn(new UsernamePasswordAuthenticationToken("userName",
//                "password"));
        mvc.perform(
                get("/announcement/getAll")
                        .params(requestParams))
                .andExpect(status().isOk());
    }
}