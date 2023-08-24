package com.goalddae.service;

import com.goalddae.repository.FriendAcceptRepository;
import com.goalddae.repository.FriendAddRepository;
import com.goalddae.repository.FriendBlockRepository;
import com.goalddae.repository.FriendListRepository;
import com.goalddae.util.MyBatisUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendServiceImpl implements FriendService{

    private final FriendListRepository friendListRepository;
    private final FriendAddRepository friendAddRepository;
    private final FriendAcceptRepository friendAcceptRepository;
    private final FriendBlockRepository friendBlockRepository;

    public FriendServiceImpl(FriendListRepository friendListRepository ,
                             FriendAddRepository friendAddRepository,
                             FriendAcceptRepository friendAcceptRepository,
                             FriendBlockRepository friendBlockRepository) {
        this.friendListRepository = friendListRepository;
        this.friendAddRepository = friendAddRepository;
        this.friendAcceptRepository = friendAcceptRepository;
        this.friendBlockRepository = friendBlockRepository;
    }

    // 동적테이블 생성 - 친구리스트
    @Override
    @Transactional
    public boolean createFriendListTable(Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendListRepository.createFriendListTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    // 동적테이블 생성 - 친구추가
    @Override
    @Transactional
    public boolean createFriendAddTable(@Param("userId") Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendAddRepository.createFriendAddTable(safeTable);
            return true;
    } catch (Exception e) {
        System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
        return false;
    }
}

    // 동적테이블 생성 - 친구수락
    @Override
    @Transactional
    public boolean createFriendAcceptTable(@Param("userId") Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendAcceptRepository.createFriendAcceptTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
}

    // 동적테이블 생성 - 친구차단
    @Override
    @Transactional
    public boolean createFriendBlockTable(@Param("userId") Long userId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(userId);
            friendBlockRepository.createFriendBlockTable(safeTable);
            return true;
        } catch (Exception e) {
        System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
        return false;
    }
}
}
