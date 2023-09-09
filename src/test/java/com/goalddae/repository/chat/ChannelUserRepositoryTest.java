package com.goalddae.repository.chat;

import com.goalddae.entity.ChannelUser;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
public class ChannelUserRepositoryTest {
    @Autowired
    private ChannelUserRepository channelUserRepository;

    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    @Transactional
    @DisplayName("해당 유저의 채팅방 조회")
    public void findChannelIdByUserId() {
        long userId = 1;

        List<ChannelUser> channelUserList = channelUserRepository.findByUserId(userId);

        assertEquals(3, channelUserList.get(0).getChannelId());
    }
}
