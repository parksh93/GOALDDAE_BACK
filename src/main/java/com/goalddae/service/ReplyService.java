package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.ReplyListDTO;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.entity.ReportedReply;

import java.util.List;

public interface ReplyService {

    List<ReplyListDTO> findAllByBoardId(long boardId);

    void deleteByUser(long replyId);

    void deleteByAdmin(long replyId);

    void save(CommunicationReply communicationReply);

    void update(CommunicationReply communicationReply);

    List<ReportedReply> findAllReportedReply();

    void saveReportedReply(ReportedReply reportedReply);

    void rejectReportedReply(long reportId);

    void approveReportedReply(long reportId);
}
