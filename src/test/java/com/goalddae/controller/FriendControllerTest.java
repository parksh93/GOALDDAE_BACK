package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.friend.AddFriendRequestDTO;
import com.goalddae.dto.friend.FriendRequestDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.FriendDTO;
import com.goalddae.dto.friend.friendBlock.UnblockFriendDTO;
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
        FriendRequestDTO friendRequestDTO = FriendRequestDTO.builder()
                .toUser(2)
                .fromUser(1)
                .build();

        String url = "/friend/deleteFriendRequest";

        final String request = objectMapper.writeValueAsString(friendRequestDTO);

        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).content(request));
    }

    @Test
    @Transactional
    @DisplayName("친구 추가")
    public void addFriend() throws Exception {
        FriendRequestDTO friendRequestDTO = FriendRequestDTO.builder()
                .toUser(2)
                .fromUser(1)
                .build();

        String url = "/friend/addFriend";
        String url2 = "/friend/findFriendList";

        final  String request = objectMapper.writeValueAsString(friendRequestDTO);

        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(request));

        FindFriendRequestDTO findFriendListRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        final String request2 = objectMapper.writeValueAsString(findFriendListRequestDTO);

        ResultActions result = mockMvc.perform(post(url2).contentType(MediaType.APPLICATION_JSON).content(request2).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("넵이"));
    }


    @Test
    @Transactional
    @DisplayName("친구 삭제")
    public void deleteFriend() throws Exception {
        FriendDTO deleteFriendDTO = FriendDTO.builder()
                .userId(1)
                .friendId(2)
                .build();

        String url = "/friend/deleteFriend";
        String url2 = "/friend/findFriendList";

        final String request = objectMapper.writeValueAsString(deleteFriendDTO);

        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).content(request));

        FindFriendRequestDTO findFriendListRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        final String request2 = objectMapper.writeValueAsString(findFriendListRequestDTO);

        ResultActions result = mockMvc.perform(post(url2).contentType(MediaType.APPLICATION_JSON).content(request2).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    @DisplayName("친구 차단목록 조회")
    public void findFriendBlockList() throws Exception {
        long userId = 1;
        String url = "/friend/findFriendBlockList/" + userId;

        ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    @Transactional
    @DisplayName("친구 차단목록 조회")
    public void blockFriend() throws Exception {
        FriendDTO friendDTO = FriendDTO.builder()
                .userId(1)
                .friendId(2)
                .build();
        String url = "/friend/blockFriend";

        final String request = objectMapper.writeValueAsString(friendDTO);
        mockMvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(request));

        long userId = 1;
        String url2 = "/friend/findFriendBlockList/" + userId;

        ResultActions resultActions = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("넵이"));
    }

    /*
    @Test
    @Transactional
    @DisplayName("친구 차단 해제")
    public void unBlockFriendTest() throws Exception{
        UnblockFriendDTO unBlockFriendDTO = UnblockFriendDTO.builder()
                .friendId(2L)
                .userId(1L)
                .build();

        String url = "/friend/unblockFriend";
        final String request = objectMapper.writeValueAsString(unBlockFriendDTO);
        mockMvc.perform(delete(url).contentType(MediaType.APPLICATION_JSON).content(request));

        long userId = 1;
        String url2 = "/friend/findFriendBlockList/" + userId;

        ResultActions resultActions = mockMvc.perform(get(url2).accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
*/

}
