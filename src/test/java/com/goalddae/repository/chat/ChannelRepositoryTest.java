package com.goalddae.repository.chat;

import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChannelRepositoryTest {
    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserJPARepository userJPARepository;


}
