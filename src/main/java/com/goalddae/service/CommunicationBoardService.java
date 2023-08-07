package com.goalddae.service;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.repository.CommunicationBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunicationBoardService {
    private final CommunicationBoardRepository communicationBoardRepository;

    @Autowired
    public CommunicationBoardService(CommunicationBoardRepository communicationBoardRepository) {
        this.communicationBoardRepository = communicationBoardRepository;
    }

    public List<CommunicationBoard> getUserPosts(String writer) {
        return communicationBoardRepository.findByWriter(writer);
    }
}