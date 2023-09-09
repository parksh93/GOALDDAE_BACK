package com.goalddae.controller.chat;

import com.goalddae.dto.chat.GetChannelListDTO;
import com.goalddae.dto.chat.MessageDTO;
import com.goalddae.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @RequestMapping("/getChannelList/{userId}")
    public List<GetChannelListDTO> getChannelList(@PathVariable Long userId){
        return chatService.getChennelList(userId);
    }

    @RequestMapping("/getMessageList/{channelId}")
    public List<MessageDTO> getMessageList(@PathVariable Long channelId){
        return chatService.getMessageList(channelId);
    }
}
