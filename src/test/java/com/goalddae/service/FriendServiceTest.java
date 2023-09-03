package com.goalddae.service;

import com.goalddae.dto.friend.SearchFriendDTO;
import com.goalddae.dto.friend.SelectFriendListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .nickname("뉴")
                .build();

        List<SearchFriendDTO> searchFriendDTOList = friendService.searchFriend(selectFriendListDTO);

        assertEquals(searchFriendDTOList.get(0).getNickname(), "안녕뉴비야");
    }
}
