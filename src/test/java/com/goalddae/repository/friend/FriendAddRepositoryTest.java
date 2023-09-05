package com.goalddae.repository.friend;

import com.goalddae.dto.friend.AddFriendRequestDTO;
import com.goalddae.dto.friend.FriendRequestDTO;
import com.goalddae.dto.friend.friendAdd.FindFriendAddDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.friendAdd.SelectToUserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FriendAddRepositoryTest {
    @Autowired
    private FriendAddRepository friendAddRepository;

    @Test
    @Transactional
    @DisplayName("신청 친구 아이디 가져오기")
    public void selectToUser() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<SelectToUserDTO> selectToUserDTOList = friendAddRepository.selectToUser(findFriendRequestDTO);

        assertEquals(selectToUserDTOList.size(), 0);
    }

    @Test
    @Transactional
    @DisplayName("내가 신청한 친구 목록 가져오기")
    public void findFriendAddTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<SelectToUserDTO> selectToUserDTOList = friendAddRepository.selectToUser(findFriendRequestDTO);

        List<FindFriendAddDTO> friendAddDTOList = friendAddRepository.findFriendAdd(selectToUserDTOList);

        assertEquals(friendAddDTOList.get(0).getNickname(), "넵이");
        assertEquals(friendAddDTOList.get(0).getAccept(), 3);
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 추가")
    public void addFriendAddTest() {
        AddFriendRequestDTO addFriendDTO = AddFriendRequestDTO.builder()
                .fromUser(1)
                .toUser(2)
                .build();

        friendAddRepository.addFriendAdd(addFriendDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<SelectToUserDTO> selectToUserDTOList = friendAddRepository.selectToUser(findFriendRequestDTO);

        assertEquals(selectToUserDTOList.size(), 1);
        assertEquals(selectToUserDTOList.get(0).getToUser(), 2);
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 목록 삭제")
    public void deleteFreindAdd() {
        FriendRequestDTO friendRequestDTO = FriendRequestDTO.builder()
                .toUser(2)
                .fromUser(1)
                .build();

        friendAddRepository.deleteFriendAdd(friendRequestDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<SelectToUserDTO> selectToUserDTOList = friendAddRepository.selectToUser(findFriendRequestDTO);

        assertEquals(selectToUserDTOList.size(), 0);

    }
}
