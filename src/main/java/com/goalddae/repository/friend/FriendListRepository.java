package com.goalddae.repository.friend;

import com.goalddae.dto.friend.*;
import com.goalddae.dto.friend.friendList.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendListRepository {
    void createFriendListTable(@Param("id") Long userId);
    List<SearchFriendDTO> searchFriendList(SelectFriendListDTO selectFriendListDTO);
    List<SearchFriendDTO> searchUnFriendList(SelectFriendListDTO selectFriendListDTO);
    List<FindFriendListResponseDTO> findFriendList(FindFriendRequestDTO findFriendRequestDTO);
    void insertFriend(AddFriendDTO addFriendDTO);
    void deleteFriend(FriendDTO friendDTO);

}
