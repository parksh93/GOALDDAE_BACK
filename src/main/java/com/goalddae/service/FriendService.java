package com.goalddae.service;

import com.goalddae.dto.friend.*;
import com.goalddae.dto.friend.friendAccept.FindFriendAcceptDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendAdd.FindFriendAddDTO;
import com.goalddae.dto.friend.friendBlock.FindFriendBlockDTO;
import com.goalddae.dto.friend.friendBlock.UnblockFriendDTO;
import com.goalddae.dto.friend.friendList.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FriendService {
    boolean createFriendAddTable(@Param("userId") Long userId);
    boolean createFriendAcceptTable(@Param("userId") Long userId);
    boolean createFriendListTable(@Param("userId") Long userId);
    Map<String, List<SearchFriendDTO>> searchFriend(SelectFriendListDTO selectFriendListDTO);
    List<FindFriendListResponseDTO> findFriendList(FindFriendRequestDTO findFriendListRequestDTO);
    List<FindFriendAcceptDTO> findAcceptList(FindFriendRequestDTO findFriendRequestDTO);
    void addFriendRequest(AddFriendRequestDTO addFriendRequestDTO);
    List<FindFriendAddDTO> findFriendAddList(FindFriendRequestDTO findFriendRequestDTO);
    void friendRejection(FriendRejectionDTO friendRejectionDTO);
    void deleteFriendRequest(FriendRequestDTO friendRequestDTO);
    void addFriend(FriendRequestDTO friendRequestDTO);
    void deleteFriend(FriendDTO friendDTO);
    void blockFriend(FriendDTO friendDTO);
    List<FindFriendBlockDTO> findFriendBlockList(long userId);
    void unblockFriend(UnblockFriendDTO unBlockFriendDTO);
}
