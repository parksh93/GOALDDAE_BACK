package com.goalddae.service;

import com.goalddae.entity.CommunicationReply;
import com.goalddae.repository.ReplyJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService{


    ReplyJPARepository replyJPARepository;

    @Autowired
    public ReplyServiceImpl(ReplyJPARepository replyJPARepository){
        this.replyJPARepository = replyJPARepository;
    }

    @Override
    public void save(CommunicationReply communicationReply) {
       replyJPARepository.save(communicationReply);
    }
}
