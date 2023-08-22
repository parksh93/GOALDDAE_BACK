package com.goalddae.service;

import com.goalddae.repository.FriendAcceptRepository;
import com.goalddae.repository.FriendAddRepository;
import com.goalddae.repository.FriendBlockRepository;
import com.goalddae.repository.FriendListRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        friendAcceptRepository.createFriendAcceptTable(friendAccept);
    }

    @Override
    @Transactional
    public void createFriendBlockTable(String friendBlock) {
        friendBlockRepository.createFriendBlockTable(friendBlock);
    }
}
