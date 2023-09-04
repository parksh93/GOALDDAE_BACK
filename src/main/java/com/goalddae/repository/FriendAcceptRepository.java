package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FriendAcceptRepository {
    void createFriendAcceptTable(@Param("id") Long userId);
}
