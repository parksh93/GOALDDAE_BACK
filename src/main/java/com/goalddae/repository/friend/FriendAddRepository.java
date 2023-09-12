package com.goalddae.repository.friend;

import com.goalddae.dto.friend.AddFriendRequestDTO;
import com.goalddae.dto.friend.FriendRequestDTO;
import com.goalddae.dto.friend.friendAdd.FindFriendAddDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.friendAdd.SelectToUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendAddRepository {
    void createFriendAddTable(@Param("id") Long userId);
    List<SelectToUserDTO> selectToUser(FindFriendRequestDTO findFriendRequestDTO);
    List<FindFriendAddDTO> findFriendAdd(List<SelectToUserDTO> selectToUserDTOList);
    void addFriendAdd(AddFriendRequestDTO addFriendRequestDTO);
    void deleteFriendAdd(FriendRequestDTO friendRequestDTO);
}
