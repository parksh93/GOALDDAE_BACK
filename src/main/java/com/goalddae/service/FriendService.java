package com.goalddae.service;

import com.goalddae.dto.friend.SearchFriendDTO;
import com.goalddae.dto.friend.SelectFriendListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendService {
    boolean createFriendAddTable(@Param("userId") Long userId);
    boolean createFriendAcceptTable(@Param("userId") Long userId);
    boolean createFriendBlockTable(@Param("userId") Long userId);
    boolean createFriendListTable(@Param("userId") Long userId);
    List<SearchFriendDTO> searchFriend(SelectFriendListDTO selectFriendListDTO);
}
