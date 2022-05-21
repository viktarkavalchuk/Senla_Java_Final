package com.senla.course.controller;

import com.senla.course.security.model.AuthRequest;
import com.senla.course.security.service.UserDetailService;
import com.senla.course.security.utils.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthenticateController {

    private static final Logger logger = LogManager.getLogger();

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticateController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
    }

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest, Model model) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                            authRequest.getPassword())
            );

            String string = jwtUtil.generateToken(authRequest.getUserName());
            model.addAttribute("Token", string);
            System.out.println("USER: " + userDetailService.loadUserByUsername("Admin").getAuthorities());

        }catch (Exception e){
            logger.error("Invalid username or password" + e);
        }
        return "/auth";
    }

}
