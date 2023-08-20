package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendListRepository {
    void createFriendListTable(String friendList);
}
