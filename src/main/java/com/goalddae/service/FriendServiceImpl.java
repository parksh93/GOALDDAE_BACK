package com.goalddae.service;

import com.goalddae.repository.FriendAcceptRepository;
import com.goalddae.repository.FriendAddRepository;
import com.goalddae.repository.FriendBlockRepository;
import com.goalddae.repository.FriendListRepository;
import com.goalddae.util.MyBatisUtil;
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

    @Override
    @Transactional
    public void createFriendListTable(String friendList) {
        friendListRepository.createFriendListTable(friendList);
    }

    @Override
    @Transactional
    public void createFriendAddTable(String friendAdd) {
        friendAddRepository.createFriendAddTable(friendAdd);
    }

    @Override
    @Transactional
    public void createFriendAcceptTable(String friendAccept) {
        String safeTable = MyBatisUtil.safeTable(friendAccept);
        friendAcceptRepository.createFriendAcceptTable(safeTable);
    }

    @Override
    @Transactional
    public void createFriendBlockTable(String friendBlock) {
        String safeTable = MyBatisUtil.safeTable(friendBlock);
        friendBlockRepository.createFriendBlockTable(safeTable);
    }
}
