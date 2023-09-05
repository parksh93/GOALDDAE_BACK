package com.goalddae.service;

import com.goalddae.dto.friend.*;
import com.goalddae.dto.friend.friendAccept.FindFriendAcceptDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendAccept.SelectFromUserDTO;
import com.goalddae.dto.friend.friendAdd.FindFriendAddDTO;
import com.goalddae.dto.friend.friendAdd.SelectToUserDTO;
import com.goalddae.dto.friend.friendList.FindFriendListResponseDTO;
import com.goalddae.dto.friend.friendList.SearchFriendDTO;
import com.goalddae.dto.friend.friendList.SelectFriendListDTO;
import com.goalddae.repository.friend.FriendAcceptRepository;
import com.goalddae.repository.friend.FriendAddRepository;
import com.goalddae.repository.friend.FriendBlockRepository;
import com.goalddae.repository.friend.FriendListRepository;
import com.goalddae.util.MyBatisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{
    private final FriendListRepository friendListRepository;
    private final FriendAddRepository friendAddRepository;
    private final FriendAcceptRepository friendAcceptRepository;
    private final FriendBlockRepository friendBlockRepository;

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

    // 동적테이블 생성 - 친구차단
    @Override
    @Transactional
    public boolean createFriendBlockTable(@Param("userId") Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendBlockRepository.createFriendBlockTable(safeTable);
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

}
