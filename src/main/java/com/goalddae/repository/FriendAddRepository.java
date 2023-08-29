package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendAddRepository {
    void createFriendAddTable(@Param("id") Long userId);
}
