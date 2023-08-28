package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendAddRepository {
    void createFriendAddTable(String friendAdd);
}
