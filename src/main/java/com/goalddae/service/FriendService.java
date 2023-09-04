package com.goalddae.service;

import com.goalddae.dto.friend.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FriendService {
    boolean createFriendAddTable(@Param("userId") Long userId);
    boolean createFriendAcceptTable(@Param("userId") Long userId);
    boolean createFriendBlockTable(@Param("userId") Long userId);
    boolean createFriendListTable(@Param("userId") Long userId);
    Map<String, List<SearchFriendDTO>> searchFriend(SelectFriendListDTO selectFriendListDTO);
    List<FindFriendListResponseDTO> findFriendList(FindFriendRequestDTO findFriendListRequestDTO);
    List<FindFriendAcceptDTO> findAcceptList(FindFriendRequestDTO findFriendRequestDTO);
}
