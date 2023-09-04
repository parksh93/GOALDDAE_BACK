package com.goalddae.repository;

import com.goalddae.dto.friend.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendListRepository {
    void createFriendListTable(@Param("id") Long userId);
    List<SearchFriendDTO> searchFriendList(SelectFriendListDTO selectFriendListDTO);
    List<SearchFriendDTO> searchUnFriendList(SelectFriendListDTO selectFriendListDTO);
    List<FindFriendListResponseDTO> findFriendList(FindFriendRequestDTO findFriendRequestDTO);

}
