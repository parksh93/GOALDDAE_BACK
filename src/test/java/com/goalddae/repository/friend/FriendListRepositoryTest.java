package com.goalddae.repository.friend;

import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.FindFriendListResponseDTO;
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
    public void searchFriendListTest() {
        SelectFriendListDTO selectFriendListDTO = SelectFriendListDTO.builder()
                .nickname("안")
                .userId(1)
                .build();

        List<SearchFriendDTO> searchFriendDTOList = friendListRepository.searchFriendList(selectFriendListDTO);

        assertEquals(searchFriendDTOList.get(0).getNickname(), "안녕뉴비야");
    }
    @Test
    @Transactional
    @DisplayName("닉네임에 맞는 유저 리스트 가져오기")
    public void searchUnFriendListTest() {
        SelectFriendListDTO selectFriendListDTO = SelectFriendListDTO.builder()
                .nickname("안")
                .userId(1)
                .build();

        List<SearchFriendDTO> searchUnFriendDTOList = friendListRepository.searchUnFriendList(selectFriendListDTO);

        assertEquals(searchUnFriendDTOList.size(),4);
        assertEquals(searchUnFriendDTOList.get(0).getFriendAddCnt(), 1);
    }

    @Test
    @Transactional
    @DisplayName("친구 리스트 가져오기")
    public void findFriendListTest(){
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();
        
        List<FindFriendListResponseDTO> findFriendListResponseDTO = friendListRepository.findFriendList(findFriendRequestDTO);
        
        assertEquals(findFriendListResponseDTO.get(0).getNickname(), "안녕뉴비야");
    }
}
