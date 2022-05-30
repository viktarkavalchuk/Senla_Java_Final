package com.senla.course.rest.controller;

import com.senla.course.announcementPlatform.model.Chat;
import com.senla.course.announcementPlatform.model.User;
import com.senla.course.announcementPlatform.service.ChatServiceImpl;
import com.senla.course.announcementPlatform.service.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.senla.course.security.dao.UserSecurityDao.idUserLogin;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private static final Logger logger = LogManager.getLogger();

    private final ChatServiceImpl chatService;
    private final UserServiceImpl userService;

    public ChatController(ChatServiceImpl chatService, UserServiceImpl userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping("/getMessage")
    public String getAll(@RequestParam(value = "Recipient") String recipient,
                         Model model){
        User user = userService.getById(idUserLogin);
        List<Chat> chats = chatService.getChatByUser(user.getLogin(), recipient);
        model.addAttribute("Chats", chats);

        return "chat/all";
    }

    @Secured("ROLE_USER")
    @PostMapping
    public String createChatMessage(@RequestParam(value = "recipient") String recipient,
                                    @RequestParam(value = "message") String message,
                                    Model model){

        User user = userService.getById(idUserLogin);
        Chat chat = new Chat();
        chat.setChatSender(user);
        chat.setChatRecipient(userService.getByLogin(recipient));
        chat.setMessage(message);

        chatService.create(chat);
        model.addAttribute(chat);
        return "chat/create";
    }
}
