package com.goalddae.controller.chat;

import com.goalddae.dto.chat.MessageDTO;
import com.goalddae.entity.Channel;
import com.goalddae.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ChatWebSocketController {
    private static Set<Integer> userList = new HashSet<>();

    private SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @Autowired
    public ChatWebSocketController(SimpMessagingTemplate simpMessagingTemplate, ChatService chatService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/chat/{id}")
    public void sendMessage(@Payload MessageDTO messageDTO, @DestinationVariable Integer id){
        messageDTO.setSendDate(LocalDateTime.now());
        chatService.saveNewMessage(messageDTO);
        this.simpMessagingTemplate.convertAndSend("/queue/addChatToClient/"+ id, messageDTO);
    }

    @MessageMapping("/chat/join")
    public void joinUser(@Payload Integer userId){
        userList.add(userId);
    }
}
