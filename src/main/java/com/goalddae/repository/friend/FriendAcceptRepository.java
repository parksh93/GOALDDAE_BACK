package com.goalddae.repository.friend;

import com.goalddae.dto.friend.AddFriendRequestDTO;
import com.goalddae.dto.friend.FriendRequestDTO;
import com.goalddae.dto.friend.friendAccept.FindFriendAcceptDTO;
import com.goalddae.dto.friend.FindFriendRequestDTO;
import com.goalddae.dto.friend.friendAccept.FriendRejectionDTO;
import com.goalddae.dto.friend.friendAccept.SelectFromUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendAcceptRepository {
    void createFriendAcceptTable(@Param("id") Long userId);
    List<FindFriendAcceptDTO> findFriendAcceptList(List<SelectFromUserDTO> selectFromUserDTOList);
    List<SelectFromUserDTO> selectFromUser(FindFriendRequestDTO findFriendRequestDTO);
    void addFriendAccept(AddFriendRequestDTO addFriendRequestDTO);
    void updateFriendAccept(FriendRejectionDTO friendRejectionDTO);
    void deleteFriendAccept(FriendRequestDTO friendRequestDTO);
}
