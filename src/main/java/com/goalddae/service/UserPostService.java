package com.goalddae.service;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.repository.CommunicationBoardRepository;
import com.goalddae.repository.UsedTransactionBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPostService {

    private CommunicationBoardRepository communicationBoardRepository;
    private UsedTransactionBoardRepository usedTransactionBoardRepository;

    @Autowired
    public UserPostService(CommunicationBoardRepository communicationBoardRepository,
                           UsedTransactionBoardRepository usedTransactionBoardRepository) {
        this.communicationBoardRepository = communicationBoardRepository;
        this.usedTransactionBoardRepository = usedTransactionBoardRepository;
    }

    public List<CommunicationBoard> getUserCommunicationBoardPosts(long id) {
        return communicationBoardRepository.findPostById(id);
    }
    public List<UsedTransactionBoard> getUserUsedTransactionBoardPosts(long id) {
        return usedTransactionBoardRepository.findPostById(id);
    }



}
