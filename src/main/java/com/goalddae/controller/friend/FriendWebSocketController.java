package com.goalddae.controller.friend;

import com.goalddae.dto.friend.FriendSocketMsgDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class FriendWebSocketController {
    private static Set<Integer> userList = new HashSet<>();

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public FriendWebSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/friend/{id}")
    public void sendMessage(@Payload FriendSocketMsgDTO friendSocketMsgDTO, @DestinationVariable Integer id){
        this.simpMessagingTemplate.convertAndSend("/queue/FriendRequestToClient/"+ id, friendSocketMsgDTO);
    }

    @MessageMapping("/friend/join")
    public void joinUser(@Payload Integer userId){
        userList.add(userId);
    }
}
