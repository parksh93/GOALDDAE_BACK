package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendAcceptRepository {
    void createFriendAcceptTable(String friendAccept);
}
