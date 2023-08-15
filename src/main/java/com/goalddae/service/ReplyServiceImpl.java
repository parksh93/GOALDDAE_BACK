package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.ReplyListDTO;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.entity.ReportedReply;
import com.goalddae.repository.ReplyJPARepository;
import com.goalddae.repository.ReportedReplyJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReplyServiceImpl implements ReplyService{


    ReplyJPARepository replyJPARepository;
    ReportedReplyJPARepository reportedReplyJPARepository;

    @Autowired
    public ReplyServiceImpl(ReplyJPARepository replyJPARepository, ReportedReplyJPARepository reportedReplyJPARepository){
        this.replyJPARepository = replyJPARepository;
        this.reportedReplyJPARepository = reportedReplyJPARepository;
    }

    @Override
    public List<ReplyListDTO> findAllByBoardId(long boardId) {
        List<CommunicationReply> allReplies = replyJPARepository.findByBoardId(boardId);
        List<ReplyListDTO> resultList = new ArrayList<>();
        Map<Long, List<CommunicationReply>> replyMap = new HashMap<>();

        // 전체 댓글을 Map에 parentId를 기준으로 그룹화하여 저장
        for (CommunicationReply reply : allReplies) {
            long parentId = reply.getParentId();

            if(parentId != 0 && reply.getStatus() != 0){
                continue;
            } // 부모id가 존재하는 댓글이면서 삭제 상태일경우 map에 포함하지 않음

            replyMap.putIfAbsent(parentId, new ArrayList<>());
            replyMap.get(parentId).add(reply);
        }

        // 부모id가 없는 상위 댓글 추출 및 자식 댓글 세팅
        List<CommunicationReply> topReplies = replyMap.getOrDefault(0L, new ArrayList<>());
        for (CommunicationReply topReply : topReplies) {
            List<CommunicationReply> children = replyMap.getOrDefault(topReply.getId(), new ArrayList<>());

            if(topReply.getStatus() != 0 && children.isEmpty()){
                continue;
            } // 상위 댓글이면서 자식댓글이 존재하지 않을경우 list에 포함하지 않음

            ReplyListDTO replyListDTO = ReplyListDTO.builder()
                    .id(topReply.getId())
                    .boardId(topReply.getBoardId())
                    .content(topReply.getContent())
                    .writer(topReply.getWriter())
                    .userId(topReply.getUserId())
                    .writeDate(topReply.getReplyWriteDate())
                    .updateDate(topReply.getReplyUpdateDate())
                    .status(topReply.getStatus())
                    .children(children)
                    .build();

            resultList.add(replyListDTO);
        }

        return resultList;
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

    @Transactional
    @Override
    public void save(CommunicationReply communicationReply) {
       replyJPARepository.save(communicationReply);
    }

    @Transactional
    @Override
    public void update(CommunicationReply communicationReply) {

        CommunicationReply targetReply = replyJPARepository.findById(communicationReply.getId()).get();

        CommunicationReply updatedReply = CommunicationReply.builder()
                .id(targetReply.getId())
                .boardId(targetReply.getBoardId())
                .parentId(targetReply.getParentId())
                .userId(targetReply.getUserId())
                .writer(targetReply.getWriter())
                .content(communicationReply.getContent())
                .replyWriteDate(targetReply.getReplyWriteDate())
                .replyUpdateDate(LocalDateTime.now())
                .status(targetReply.getStatus())
                .build();

        replyJPARepository.save(updatedReply);
    }

    @Override
    public List<ReportedReply> findAllReportedReply() {
        return reportedReplyJPARepository.findAll();
    }

    @Transactional
    @Override
    public void saveReportedReply(ReportedReply reportedReply) {
        reportedReplyJPARepository.save(reportedReply);
    }

    @Transactional
    @Override
    public void rejectReportedReply(long reportId) {
        reportedReplyJPARepository.deleteById(reportId);
    }

    @Transactional
    @Override
    public void approveReportedReply(long reportId) {
        ReportedReply reportedReply = reportedReplyJPARepository.findById(reportId).get();
        CommunicationReply communicationReply = replyJPARepository.findById(reportedReply.getReplyId()).get();

        communicationReply.deleteByAdmin();
        reportedReplyJPARepository.delete(reportedReply);
    }


}
