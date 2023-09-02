package com.goalddae.service;

import org.apache.ibatis.annotations.Param;

public interface FriendService {
    boolean createFriendAddTable(@Param("userId") Long userId);
    boolean createFriendAcceptTable(@Param("userId") Long userId);
    boolean createFriendBlockTable(@Param("userId") Long userId);
    boolean createFriendListTable(@Param("userId") Long userId);
}
