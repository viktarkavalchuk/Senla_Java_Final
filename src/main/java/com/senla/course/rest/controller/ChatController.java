package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.ChatDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private final ChatServiceImpl chatService;
    private final UserServiceImpl userService;
    private final BasicConverter<Chat, ChatDto> converter;

    public ChatController(ChatServiceImpl chatService, UserServiceImpl userService, BasicConverter<Chat, ChatDto> converter) {
        this.chatService = chatService;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping("/getMessage")
    public ResponseEntity<?> getAll(@RequestParam(value = "Recipient") String recipient) {
        User user = userService.getById(idUserLogin);
        List<Chat> chats = chatService.getChatByUser(user.getLogin(), recipient);

        return new ResponseEntity<>(chats.stream().map(d -> converter.convertToDto(d, ChatDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<?> createChatMessage(@RequestParam(value = "recipient") String recipient,
                                               @RequestParam(value = "message") String message) {

        User user = userService.getById(idUserLogin);
        Chat chat = new Chat();
        chat.setChatSender(user);
        chat.setChatRecipient(userService.getByLogin(recipient));
        chat.setMessage(message);
        chatService.create(chat);

        return new ResponseEntity<>(converter.convertToDto(chat, ChatDto.class), HttpStatus.OK);
    }
}
