package com.goalddae.repository.friend;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendBlockRepository {
    void createFriendBlockTable(@Param("id") Long userId);
}
