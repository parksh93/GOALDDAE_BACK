package com.goalddae.repository.friend;

import com.goalddae.dto.friend.FindFriendAcceptDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.repository.FriendAcceptRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FindAcceptRepository {
    @Autowired
    private FriendAcceptRepository friendAcceptRepository;

    @Test
    @Transactional
    @DisplayName("친구 수락 리스트 가져오기")
    public void findAcceptListTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendAcceptDTO> findFriendAcceptDTOList =  friendAcceptRepository.findFriendAcceptList(findFriendRequestDTO);

        assertEquals(findFriendAcceptDTOList.get(0).getNickname(), "안수");
    }

}
