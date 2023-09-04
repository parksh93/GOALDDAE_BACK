package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.ReplyListDTO;
import com.goalddae.dto.board.ReplyUpdateDTO;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.entity.ReportedReply;

import java.util.List;

public interface ReplyService {

    List<ReplyListDTO> findAllByBoardId(long boardId);

    long replyCount(List<ReplyListDTO> replyListDTOs);

    void deleteByUser(long replyId);

    void deleteByAdmin(long replyId);

    void save(CommunicationReply communicationReply);

    void update(ReplyUpdateDTO replyUpdateDTO);

    List<ReportedReply> findAllReportedReply();

    void saveReportedReply(ReportedReply reportedReply);

    void rejectReportedReply(long reportId);

    void approveReportedReply(long reportId);
}
