package com.goalddae.service;

import com.goalddae.dto.chat.GetChannelListDTO;
import com.goalddae.dto.chat.MessageDTO;

import java.util.List;

public interface ChatService {
    public List<GetChannelListDTO> getChennelList(Long userId);
    public void saveNewMessage(MessageDTO messageDTO);

    public List<MessageDTO> getMessageList(Long channelId);
}
