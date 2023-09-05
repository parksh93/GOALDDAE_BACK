package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.friend.AddFriendRequestDTO;
import com.goalddae.dto.friend.FriendRequestDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendList.SelectFriendListDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void searchTest() throws Exception{
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
    public void findFriendListTest() throws Exception {
        FindFriendRequestDTO findFriendListRequestDTO = FindFriendRequestDTO.builder().userId(1).build();
        String url = "/friend/findFriendList";

        final String request = objectMapper.writeValueAsString(findFriendListRequestDTO);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("안녕뉴비야"));
    }

    @Test
    @Transactional
    @DisplayName("친구 수락 리스트 조회")
    public void findFriendAcceptTest() throws Exception {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();
        String url = "/friend/findFriendAccept";

        final String request = objectMapper.writeValueAsString(findFriendRequestDTO);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("안수"));
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 추가")
    public void addFriendRequestTest() throws Exception {
        AddFriendRequestDTO addFriendRequestDTO = AddFriendRequestDTO.builder()
                .toUser(2).fromUser(1).build();
        String url = "/friend/addFriendRequest";
        String url2 = "/friend/findFriendAccept";

        final String request = objectMapper.writeValueAsString(addFriendRequestDTO);

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(request));

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(2).build();
        final String request2 = objectMapper.writeValueAsString(findFriendRequestDTO);

        ResultActions resultActions = mockMvc.perform(post(url2).contentType(MediaType.APPLICATION_JSON).content(request2).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("구글ㄹ"));

    }

    @Test
    @Transactional
    @DisplayName("내가 신청한 친구 리스트 조회")
    public void findFriendAddListTest () throws Exception {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();
        String url = "/friend/findFriendAdd";

        final String request = objectMapper.writeValueAsString(findFriendRequestDTO);

        ResultActions result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(request).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("넵이"));
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 거절")
    public void friendRejectionTest() throws Exception{
        FriendRejectionDTO friendRejectionDTO = FriendRejectionDTO.builder()
                .userId(2)
                .fromUser(1)
                .build();
        String url = "/friend/friendRejection";

        final String request = objectMapper.writeValueAsString(friendRejectionDTO);

        mockMvc.perform(patch(url).contentType(MediaType.APPLICATION_JSON).content(request));
    }

    @Test
    @Transactional
    @DisplayName("친구 요청 목록 삭제")
    public void deleteFriendRequest() throws Exception {
        FriendRequestDTO deleteFriendRequestDTO = FriendRequestDTO.builder()
                .toUser(2)
                .fromUser(1)
                .build();

        String url = "/friend/deleteFriendRequest";

        final String request = objectMapper.writeValueAsString(deleteFriendRequestDTO);

        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).content(request));
    }
}
