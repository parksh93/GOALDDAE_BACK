package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendBlockRepository {
    void createFriendBlockTable(@Param("id") Long userId);
}
