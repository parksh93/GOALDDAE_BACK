package com.goalddae.service;

import com.goalddae.dto.chat.GetChannelListDTO;
import com.goalddae.dto.chat.MessageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChatServiceTest {
    @Autowired
    private ChatService chatService;

    @Test
    @Transactional
    @DisplayName("해당 유저의 채널 정보 가져오기")
    public void getChannelListTest() {
        Long userId = 1L;

        List<GetChannelListDTO>channelListDTOList = chatService.getChennelList(userId);

        assertEquals(3, channelListDTOList.get(0).getChannelId());
    }

    @Test
    @Transactional
    @DisplayName("새로운 메시지 저장")
    public void saveNewMessageTest() {
        Long channelId = 3L;
        String content = "테스트";
        long userId = 1;

        LocalDateTime date = LocalDateTime.now();
        MessageDTO messageDTO = MessageDTO.builder()
                .channelId(channelId)
                .content(content)
                .sendDate(date)
                .userId(userId)
                .build();

        chatService.saveNewMessage(messageDTO);

        List<MessageDTO> messageDTOList = chatService.getMessageList(channelId);

        assertEquals("테스트", messageDTOList.get(0).getContent());
    }

    @Test
    @Transactional
    @DisplayName("채널의 메시지 리스트 조회")
    public void getMessageList() {
        Long channelId = 3L;

        List<MessageDTO> messageDTOList = chatService.getMessageList(channelId);

        assertEquals(1, messageDTOList.size());
    }
}
