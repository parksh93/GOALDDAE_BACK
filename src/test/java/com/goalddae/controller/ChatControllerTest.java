package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.entity.Channel;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import com.goalddae.repository.chat.ChannelRepository;
import com.goalddae.repository.chat.ChannelUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    ChannelUserRepository channelUserRepository;

    @Autowired
    UserJPARepository userJPARepository;

    @BeforeEach
    public void saveChannel (){
        channelRepository.deleteAll();
        userJPARepository.deleteAll();
        channelUserRepository.deleteAll();

        Channel channel = Channel.builder()
                .channelName("테스트")
                .channelImgUrl("없음")
                .build();

        channelRepository.save(channel);

        User user = User.builder()
                .authority("user")
                .loginId("asda12312s")
                .email("1234")
                .name("박상현")
                .signupDate(LocalDateTime.now())
                .profileUpdateDate(LocalDateTime.now())
                .build();

        userJPARepository.save(user);

    }

    @Test
    @Transactional
    @DisplayName("해당 유저의 채널 리스트 가져오기")
    public void getChannelListTest() throws Exception {
        Long userId = 1L;

        String url = "/chat/getChannelList/" + userId;

        ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].channelId").value(3));
    }

    @Test
    @Transactional
    @DisplayName("해당 채널의 메시지 리스트 조회")
    public void getMessageList() throws Exception {
        Long channelId = 1L;

        String url = "/chat/getMessageList/" + channelId;

        ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("테스트"));
    }
}
