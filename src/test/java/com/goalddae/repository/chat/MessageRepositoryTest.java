package com.goalddae.repository.chat;

import com.goalddae.entity.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    @Transactional
    @DisplayName("채널의 메시지 리스트 조회")
    public void findByChannelIdOrderBySendDateAscTest(){
        Long channelId = 3L;

        List<Message> messageList = messageRepository.findByChannelIdOrderBySendDateAsc(channelId);

        assertEquals(2, messageList.size());
    }
}
