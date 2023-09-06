package com.goalddae.repository.friend;

import com.goalddae.dto.friend.AddFriendRequestDTO;
import com.goalddae.dto.friend.FriendRequestDTO;
import com.goalddae.dto.friend.friendAccept.FindFriendAcceptDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendAccept.SelectFromUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class FriendAcceptRepositoryTest {
    @Autowired
    private FriendAcceptRepository friendAcceptRepository;

    @Test
    @Transactional
    @DisplayName("친구 신청 아이디 가져오기")
    public void selectFromUserTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<SelectFromUserDTO> selectFromUserDTOList = friendAcceptRepository.selectFromUser(findFriendRequestDTO);

        assertEquals(selectFromUserDTOList.size(), 0);
    }

    @Test
    @Transactional
    @DisplayName("친구 수락 리스트 가져오기")
    public void findAcceptListTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<SelectFromUserDTO> selectFromUserDTOList = friendAcceptRepository.selectFromUser(findFriendRequestDTO);

        List<FindFriendAcceptDTO> findFriendAcceptDTOList = new ArrayList<>();
        if(selectFromUserDTOList.size() != 0){
            findFriendAcceptDTOList =  friendAcceptRepository.findFriendAcceptList(selectFromUserDTOList);
        }

        assertEquals(findFriendAcceptDTOList.size(), 0);
    }

    @Test
    @Transactional
    @DisplayName("친구 수락 추가")
    public void addFriendAcceptTest() {
        AddFriendRequestDTO addFriendDTO = AddFriendRequestDTO.builder()
                .toUser(2)
                .fromUser(1)
                .build();

        friendAcceptRepository.addFriendAccept(addFriendDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(2).build();

        List<SelectFromUserDTO> selectFromUserDTOList = friendAcceptRepository.selectFromUser(findFriendRequestDTO);

        assertEquals(selectFromUserDTOList.size(), 1);
        assertEquals(selectFromUserDTOList.get(0).getFromUser(), 1);
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 거절")
    public void friendRejectionTest() {
        FriendRejectionDTO friendRejectionDTO = FriendRejectionDTO.builder()
                .userId(2)
                .fromUser(1)
                .build();

        friendAcceptRepository.updateFriendAccept(friendRejectionDTO);
    }

    @Test
    @Transactional
    @DisplayName("친구 수락 목록 삭제")
    public void deleteFriendAcceptTest() {
        FriendRequestDTO friendRequestDTO = FriendRequestDTO.builder()
                .toUser(2)
                .fromUser(1)
                .build();

        friendAcceptRepository.deleteFriendAccept(friendRequestDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(2).build();
        List<SelectFromUserDTO> selectFromUserDTOList = friendAcceptRepository.selectFromUser(findFriendRequestDTO);

        assertEquals(selectFromUserDTOList.size(), 0);
    }

}
