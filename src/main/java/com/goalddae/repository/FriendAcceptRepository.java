package com.goalddae.repository;

import com.goalddae.dto.friend.FindFriendAcceptDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendAcceptRepository {
    void createFriendAcceptTable(@Param("id") Long userId);
    List<FindFriendAcceptDTO> findFriendAcceptList(FindFriendRequestDTO findFriendRequestDTO);
}
