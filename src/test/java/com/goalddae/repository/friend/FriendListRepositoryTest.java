package com.goalddae.repository.friend;

import com.goalddae.dto.friend.SearchFriendDTO;
import com.goalddae.dto.friend.SelectFriendListDTO;
import com.goalddae.repository.FriendListRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FriendListRepositoryTest {
    @Autowired
    FriendListRepository friendListRepository;

    @Test
    @Transactional
    @DisplayName("닉네임에 맞는 친구 리스트 가져오기")
    public void findByNicknameContainingTest() {
        SelectFriendListDTO selectFriendListDTO = SelectFriendListDTO.builder()
                .nickname("안")
                .userId(1)
                .build();

        List<SearchFriendDTO> searchFriendDTOList = friendListRepository.findByNicknameContaining(selectFriendListDTO);

        assertEquals(searchFriendDTOList.get(0).getNickname(), "안녕뉴비야");
    }
}
