package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.ReplyListDTO;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.repository.ReplyJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService{


    ReplyJPARepository replyJPARepository;

    @Autowired
    public ReplyServiceImpl(ReplyJPARepository replyJPARepository){
        this.replyJPARepository = replyJPARepository;
    }

    @Override
    public List<ReplyListDTO> findAllByBoardId(long boardId) {
        List<CommunicationReply> topReplys = replyJPARepository.findByBoardIdAndParentId(boardId, 0);
        List<ReplyListDTO> newList = new ArrayList<>();

        for (CommunicationReply topReply : topReplys) {
            ReplyListDTO replyListDTO = ReplyListDTO.builder()
                    .id(topReply.getId())
                    .boardId(topReply.getBoardId())
                    .content(topReply.getContent())
                    .writer(topReply.getWriter())
                    .userId(topReply.getUserId())
                    .writeDate(topReply.getReplyWriteDate())
                    .updateDate(topReply.getReplyUpdateDate())
                    .children(replyJPARepository.findByBoardIdAndParentId(topReply.getBoardId(), topReply.getId()))
                    .build();

            newList.add(replyListDTO);
        }
        return newList;
    }

    @Transactional
    @Override
    public void deleteByUser(long replyId) {
        CommunicationReply communicationReply = replyJPARepository.findById(replyId).get();
        communicationReply.deleteByUser();
    }

    @Transactional
    @Override
    public void deleteByAdmin(long replyId) {
        CommunicationReply communicationReply = replyJPARepository.findById(replyId).get();
        communicationReply.deleteByAdmin();
    }

    @Override
    public void save(CommunicationReply communicationReply) {
       replyJPARepository.save(communicationReply);
    }
}
