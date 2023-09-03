package com.goalddae.repository;

import com.goalddae.dto.friend.SearchFriendDTO;
import com.goalddae.dto.friend.SelectFriendListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendListRepository {
    void createFriendListTable(@Param("id") Long userId);
    List<SearchFriendDTO> findByNicknameContaining(SelectFriendListDTO selectFriendListDTO);
}
