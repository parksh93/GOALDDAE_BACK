package com.goalddae.controller;

import com.goalddae.dto.chat.MessageDTO;
import com.goalddae.entity.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class ChatWebSocketController {
    private static Set<Integer> userList = new HashSet<>();

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{id}")
    public void sendMessage(@Payload MessageDTO messageDTO, @DestinationVariable Integer id){
        this.simpMessagingTemplate.convertAndSend("/queue/addChatToClient/"+ id, messageDTO);
    }

    @MessageMapping("/chat/join")
    public void joinUser(@Payload Integer userId){
        userList.add(userId);
        userList.forEach(user -> System.out.println(user));
    }
}
