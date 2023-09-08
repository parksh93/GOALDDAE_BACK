package com.goalddae.service;

import com.goalddae.dto.friend.*;
import com.goalddae.dto.friend.friendAccept.FindFriendAcceptDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendAccept.SelectFromUserDTO;
import com.goalddae.dto.friend.friendAdd.FindFriendAddDTO;
import com.goalddae.dto.friend.friendAdd.SelectToUserDTO;
import com.goalddae.dto.friend.friendBlock.FindFriendBlockDTO;
import com.goalddae.dto.friend.friendBlock.UnblockFriendDTO;
import com.goalddae.dto.friend.friendList.*;
import com.goalddae.entity.Channel;
import com.goalddae.entity.FriendBlock;
import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import com.goalddae.repository.chat.ChannelRepository;
import com.goalddae.repository.friend.FriendAcceptRepository;
import com.goalddae.repository.friend.FriendAddRepository;
import com.goalddae.repository.friend.FriendBlockRepository;
import com.goalddae.repository.friend.FriendListRepository;
import com.goalddae.util.MyBatisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{
    private final FriendListRepository friendListRepository;
    private final FriendAddRepository friendAddRepository;
    private final FriendAcceptRepository friendAcceptRepository;
    private final FriendBlockRepository friendBlockRepository;
    private  final ChannelRepository channelRepository;
    private final UserJPARepository userJPARepository;


    // 동적테이블 생성 - 친구리스트
    @Override
    @Transactional
    public boolean createFriendListTable(Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendListRepository.createFriendListTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    // 동적테이블 생성 - 친구추가
    @Override
    @Transactional
    public boolean createFriendAddTable(@Param("userId") Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendAddRepository.createFriendAddTable(safeTable);
            return true;
    } catch (Exception e) {
        System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
        return false;
    }
}

    // 동적테이블 생성 - 친구수락
    @Override
    @Transactional
    public boolean createFriendAcceptTable(@Param("userId") Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendAcceptRepository.createFriendAcceptTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Map<String, List<SearchFriendDTO>> searchFriend(SelectFriendListDTO selectFriendListDTO) {
        List<SearchFriendDTO> searchFriendList = friendListRepository.searchFriendList(selectFriendListDTO);
        List<SearchFriendDTO> searchUnFriendList = friendListRepository.searchUnFriendList(selectFriendListDTO);
        Map<String, List<SearchFriendDTO>> listMap = new HashMap<>();

        listMap.put("friendList", searchFriendList);
        listMap.put("unFriendList", searchUnFriendList);

        return listMap;
    }

    @Override
    public List<FindFriendListResponseDTO> findFriendList(FindFriendRequestDTO findFriendRequestDTO) {
        return friendListRepository.findFriendList(findFriendRequestDTO);
    }

    @Override
    public List<FindFriendAcceptDTO> findAcceptList(FindFriendRequestDTO findFriendRequestDTO) {
        List<SelectFromUserDTO> fromUserDTOList = friendAcceptRepository.selectFromUser(findFriendRequestDTO);

        if(fromUserDTOList.size() != 0){
            List<FindFriendAcceptDTO> friendAcceptDTOList = friendAcceptRepository.findFriendAcceptList(fromUserDTOList);
            return friendAcceptDTOList;
        }
        return null;
    }

    @Override
    public void addFriendRequest(AddFriendRequestDTO addFriendRequestDTO) {
        friendAddRepository.addFriendAdd(addFriendRequestDTO);
        friendAcceptRepository.addFriendAccept(addFriendRequestDTO);
    }

    @Override
    public List<FindFriendAddDTO> findFriendAddList(FindFriendRequestDTO findFriendRequestDTO) {
        List<SelectToUserDTO> selectToUserDTOList = friendAddRepository.selectToUser(findFriendRequestDTO);

        if(selectToUserDTOList.size() !=0){
            List<FindFriendAddDTO> friendAddDTOList = friendAddRepository.findFriendAdd(selectToUserDTOList);

            return friendAddDTOList;
        }
        return null;
    }

    @Override
    public void friendRejection(FriendRejectionDTO friendRejectionDTO) {
        friendAcceptRepository.updateFriendAccept(friendRejectionDTO);
    }

    @Override
    public void deleteFriendRequest(FriendRequestDTO friendRequestDTO) {
        friendAcceptRepository.deleteFriendAccept(friendRequestDTO);
        friendAddRepository.deleteFriendAdd(friendRequestDTO);
    }

    @Override
    public void addFriend(FriendRequestDTO friendRequestDTO) {
        friendAcceptRepository.deleteFriendAccept(friendRequestDTO);
        friendAddRepository.deleteFriendAdd(friendRequestDTO);

        AddFriendDTO addFriendDTO = new AddFriendDTO();
        addFriendDTO.setFriendId(friendRequestDTO.getToUser());
        addFriendDTO.setUserId(friendRequestDTO.getFromUser());
        friendListRepository.insertFriend(addFriendDTO);

        addFriendDTO.setFriendId(friendRequestDTO.getFromUser());
        addFriendDTO.setUserId(friendRequestDTO.getToUser());
        friendListRepository.insertFriend(addFriendDTO);

        User user1 =  userJPARepository.findById(friendRequestDTO.getToUser()).get();
        User user2 = userJPARepository.findById(friendRequestDTO.getFromUser()).get();

//        Set<User> users = new HashSet<>();
//        users.add(user1);
//        users.add(user2);
//
//        Channel channel = Channel.builder().users(users).build();
//
//        channelRepository.save(channel);

    }

    @Override
    public void deleteFriend(FriendDTO friendDTO) {
        friendListRepository.deleteFriend(friendDTO);
        long userId = friendDTO.getUserId();
        long friendId = friendDTO.getFriendId();
        friendDTO.setFriendId(userId);
        friendDTO.setUserId(friendId);
        friendListRepository.deleteFriend(friendDTO);
    }

    @Override
    public void blockFriend(FriendDTO friendDTO) {
        friendListRepository.deleteFriend(friendDTO);

        FriendBlock friendBlock = FriendBlock.builder()
                .friendId(friendDTO.getFriendId())
                .userId(friendDTO.getUserId())
                .blockDate(LocalDateTime.now())
                .build();

        friendBlockRepository.save(friendBlock);

        long userId = friendDTO.getUserId();
        long friendId = friendDTO.getFriendId();
        friendDTO.setFriendId(userId);
        friendDTO.setUserId(friendId);
        friendListRepository.deleteFriend(friendDTO);

    }

    @Override
    public List<FindFriendBlockDTO> findFriendBlockList(long userId) {
        return friendBlockRepository.findFriendBlockList(userId);
    }

    @Override
    public void unblockFriend(UnblockFriendDTO unblockFriendDTO) {
        FriendBlock block = friendBlockRepository.findByUserIdAndFriendId(unblockFriendDTO.getUserId(), unblockFriendDTO.getFriendId());
        friendBlockRepository.deleteById(block.getId());
    }

}


