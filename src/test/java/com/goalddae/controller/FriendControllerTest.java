package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.SelectFriendListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FriendControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @DisplayName("검색기능")
    public void search() throws Exception{
        SelectFriendListDTO selectFriendListDTO = SelectFriendListDTO.builder()
                .userId(1)
                .nickname("안")
                .build();

        String url = "/friend/search";

        final String request = objectMapper.writeValueAsString(selectFriendListDTO);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(request)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(jsonPath("$.friendList[0].nickname").value("안녕뉴비야"));

    }

    @Test
    @Transactional
    @DisplayName("친구 조회")
    public void findFriendList() throws Exception {
        FindFriendRequestDTO findFriendListRequestDTO = FindFriendRequestDTO.builder().userId(1).build();
        String url = "/friend/findFriendList";

        final String request = objectMapper.writeValueAsString(findFriendListRequestDTO);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("안녕뉴비야"));
    }
}
