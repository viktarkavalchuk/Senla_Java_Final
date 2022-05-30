package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.ChatDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private static final Logger logger = LogManager.getLogger();

    private final ChatServiceImpl chatService;
    private final UserServiceImpl userService;
    private final BasicConverter<Chat, ChatDto> converter;

    public ChatController(ChatServiceImpl chatService, UserServiceImpl userService, BasicConverter<Chat, ChatDto> converter) {
        this.chatService = chatService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/getMessage")
    public ResponseEntity<?> getAll(@RequestParam(value = "Recipient") String recipient){
        User user = userService.getById(idUserLogin);
        List<Chat> chats = chatService.getChatByUser(user.getLogin(), recipient);

        return new ResponseEntity<>(chats.stream().map(d -> converter.convertToDto(d, ChatDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<?> createChatMessage(@RequestParam(value = "recipient") String recipient,
                                               @RequestParam(value = "message") String message){

        User user = userService.getById(idUserLogin);
        Chat chat = new Chat();
        chat.setChatSender(user);
        chat.setChatRecipient(userService.getByLogin(recipient));
        chat.setMessage(message);
        chatService.create(chat);

        return new ResponseEntity<>(converter.convertToDto(chat, ChatDto.class), HttpStatus.OK);
    }
}
