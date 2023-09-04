package com.goalddae.service;

import com.goalddae.dto.friend.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FriendServiceTest {
    @Autowired
    FriendService friendService;

    @Test
    @Transactional
    @DisplayName("닉네임에 맞는 친구찾기")
    public void searchFriendTest() {
        SelectFriendListDTO selectFriendListDTO = SelectFriendListDTO.builder()
                .userId(1)
                .nickname("안")
                .build();

        Map< String, List<SearchFriendDTO>> searchMap = friendService.searchFriend(selectFriendListDTO);

        List<SearchFriendDTO> searchUnFriendDTOList = searchMap.get("unFriendList");

        assertEquals(searchUnFriendDTOList.size(), 4);
        assertEquals(searchUnFriendDTOList.get(0).getFriendAddCnt(), 0);
    }

    @Test
    @Transactional
    @DisplayName("친구 리스트 가져오기")
    public void findFriendListTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendListResponseDTO> findFriendListResponseDTO = friendService.findFriendList(findFriendRequestDTO);

        assertEquals(findFriendListResponseDTO.get(0).getNickname(), "안녕뉴비야");
    }

    @Test
    @Transactional
    @DisplayName("친구 수락 리스트 가져오기")
    public void findAcceptListTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendAcceptDTO> findFriendAcceptDTOList = friendService.findAcceptList(findFriendRequestDTO);

        assertEquals(findFriendAcceptDTOList.size(), 1);
    }
}
