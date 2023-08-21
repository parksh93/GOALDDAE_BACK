package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendBlockRepository {
    void createFriendBlockTable(String friendBlock);
}
