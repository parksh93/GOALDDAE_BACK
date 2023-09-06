package com.goalddae.repository.friend;

import com.goalddae.dto.friend.friendBlock.FindFriendBlockDTO;
import com.goalddae.entity.FriendBlock;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendBlockRepository extends JpaRepository<FriendBlock, Long> {
    @Query(value = "SELECT u.id, u.nickname, u.profile_img_url As profileImgUrl, f.block_date AS blockDate FROM friend_block f LEFT JOIN users u ON f.friend_id = u.id WHERE f.user_id = :userId", nativeQuery = true)
    List<FindFriendBlockDTO> findFriendBlockList(@Param("userId") long userId);
    FriendBlock findByUserIdAndFriendId(Long userId, Long friendId);
}
