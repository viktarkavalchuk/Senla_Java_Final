package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import com.senla.course.rest.converter.BasicConverter;
import com.senla.course.rest.dto.ChatDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Chat Controller", description = "Organization of personal correspondence between the buyer and the seller")
@RestController
@SecurityRequirement(name = "Bearer Authentication")
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

    @Operation(summary = "Get all messages for this authorized user")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getMessage")
    public ResponseEntity<?> getAll(@RequestParam(value = "Recipient") String recipient) {

        User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Chat> chats = chatService.getChatByUser(userRequest.getLogin(), recipient);
        return new ResponseEntity<>(chats.stream().map(d -> converter.convertToDto(d, ChatDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @Operation(summary = "Write a message to the user")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<?> createChatMessage(@RequestParam(value = "recipient") String recipient,
                                               @RequestParam(value = "message") String message) {

        User userRequest = userService.getByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Chat chat = new Chat();
        chat.setChatSender(userRequest);
        chat.setChatRecipient(userService.getByLogin(recipient));
        chat.setMessage(message);
        chatService.create(chat);

        return new ResponseEntity<>(converter.convertToDto(chat, ChatDto.class), HttpStatus.OK);
    }
}
