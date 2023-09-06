package com.goalddae.service;

import com.goalddae.dto.friend.*;
import com.goalddae.dto.friend.friendAccept.FindFriendAcceptDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendAdd.FindFriendAddDTO;
import com.goalddae.dto.friend.FriendDTO;
import com.goalddae.dto.friend.friendBlock.FindFriendBlockDTO;
import com.goalddae.dto.friend.friendBlock.UnblockFriendDTO;
import com.goalddae.dto.friend.friendList.FindFriendListResponseDTO;
import com.goalddae.dto.friend.friendList.SearchFriendDTO;
import com.goalddae.dto.friend.friendList.SelectFriendListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
    @DisplayName("친구 리스트 조회")
    public void findFriendListTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendListResponseDTO> findFriendListResponseDTO = friendService.findFriendList(findFriendRequestDTO);

        assertEquals(findFriendListResponseDTO.get(0).getNickname(), "안녕뉴비야");
    }

    @Test
    @Transactional
    @DisplayName("친구 수락 리스트 조회")
    public void findAcceptListTest(){
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendAcceptDTO> findFriendAcceptDTOList = friendService.findAcceptList(findFriendRequestDTO);

        assertEquals(findFriendAcceptDTOList.size(), 1);
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 추가")
    public void addFriendRequestTest()throws Exception{
        AddFriendRequestDTO addFriendRequestDTO = AddFriendRequestDTO
                .builder().fromUser(1).toUser(2).build();

        friendService.addFriendRequest(addFriendRequestDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(2).build();

        List<FindFriendAcceptDTO> findFriendAcceptDTOList = friendService.findAcceptList(findFriendRequestDTO);

        assertEquals(findFriendAcceptDTOList.size(), 1);
        assertEquals(findFriendAcceptDTOList.get(0).getNickname(), "구글ㄹ");
    }

    @Test
    @Transactional
    @DisplayName("내가 신청한 친구 목록 조회")
    public void findFriendAddListTest() {
        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder()
                .userId(1).build();

        List<FindFriendAddDTO> friendAddDTOList = friendService.findFriendAddList(findFriendRequestDTO);

        assertEquals(friendAddDTOList.get(0).getNickname(), "넵이");
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 거절")
    public void friendRejectionTest() {
        FriendRejectionDTO friendRejectionDTO = FriendRejectionDTO.builder()
                .userId(2)
                .fromUser(1)
                .build();

        friendService.friendRejection(friendRejectionDTO);
    }

    @Test
    @Transactional
    @DisplayName("친구 신청 기록 삭제")
    public void deleteFriendRequestTest() {
        FriendRequestDTO friendRequestDTO = FriendRequestDTO
                .builder()
                .toUser(2)
                .fromUser(1)
                .build();

        friendService.deleteFriendRequest(friendRequestDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendAcceptDTO> findFriendAcceptDTOList = friendService.findAcceptList(findFriendRequestDTO);

        assertNull(findFriendAcceptDTOList);
    }

    @Test
    @Transactional
    @DisplayName("친구 추가")
    public void addFriendTest() {
        FriendRequestDTO friendRequestDTO = FriendRequestDTO
                .builder().toUser(2).fromUser(1).build();

        friendService.addFriend(friendRequestDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendListResponseDTO> findFriendListResponseDTO = friendService.findFriendList(findFriendRequestDTO);

        assertEquals(findFriendListResponseDTO.get(0).getNickname(), "넵이");
    }

    @Test
    @Transactional
    @DisplayName("친구 삭제")
    public void deleteFriend() {
        FriendDTO deleteFriendDTO = FriendDTO.builder()
                .userId(1)
                .friendId(2)
                .build();

        friendService.deleteFriend(deleteFriendDTO);

        FindFriendRequestDTO findFriendRequestDTO = FindFriendRequestDTO.builder().userId(1).build();

        List<FindFriendListResponseDTO> findFriendListResponseDTO = friendService.findFriendList(findFriendRequestDTO);

        assertEquals(findFriendListResponseDTO.size(), 0);
    }

    @Test
    @Transactional
    @DisplayName("친구 차단")
    public void blockFriend() {
        FriendDTO friendDTO = FriendDTO.builder()
                .userId(1)
                .friendId(2)
                .build();

        friendService.blockFriend(friendDTO);

        long userId = 1;
        List<FindFriendBlockDTO> friendBlockList = friendService.findFriendBlockList(userId);

        assertEquals(friendBlockList.get(0).getNickname(), "넵이");
    }

    @Test
    @Transactional
    @DisplayName("친구 차단 목록 조회")
    public void findFriendBlockListTest() {
        long userId = 1;
        List<FindFriendBlockDTO> friendBlockList = friendService.findFriendBlockList(userId);

        assertEquals(friendBlockList.size(), 0);
    }

    @Test
    @Transactional
    @DisplayName("친구 차단 해제")
    public void unBlockFriendTest() {
        long userId = 1;
        long friendId = 2;
        UnblockFriendDTO unblockFriendDTO = UnblockFriendDTO.builder()
                        .friendId(friendId)
                                .userId(userId)
                                        .build();

        friendService.unblockFriend(unblockFriendDTO);

        List<FindFriendBlockDTO> friendBlockList = friendService.findFriendBlockList(userId);

        assertEquals(friendBlockList.size(), 0);
    }

}
