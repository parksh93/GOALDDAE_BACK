package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendListRepository {
    void createFriendListTable(@Param("id") Long userId);
}
