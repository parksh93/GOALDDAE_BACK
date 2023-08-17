package com.goalddae.service;

import com.goalddae.dto.board.ReplyListDTO;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.entity.ReportedReply;
import com.goalddae.repository.ReplyJPARepository;
import com.goalddae.repository.ReportedReplyJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    ReplyService replyService;

    @Autowired
    ReplyJPARepository replyJPARepository;

    @Autowired
    ReportedReplyJPARepository reportedReplyJPARepository;

    @Test
    @Transactional
    @DisplayName("boardId를 기준으로 전체 댓글 조회")
    public void findAllByBoardIdTest(){

        long boardId = 1;
        List<ReplyListDTO> allReplies = replyService.findAllByBoardId(boardId);

        assertEquals(allReplies.size(), 5);

    }

    @Test
    @Transactional
    @DisplayName("작성자에 의한 삭제")
    public void deleteByUserTest(){

        long replyId = 2;
        replyService.deleteByUser(replyId);

        CommunicationReply reply = replyJPARepository.findById(replyId).get();

        assertEquals(reply.getStatus(), 1);

    }

    @Test
    @Transactional
    @DisplayName("관리자에 의한 삭제")
    public void deleteByAdminTest(){

        long replyId = 2;
        replyService.deleteByAdmin(replyId);

        CommunicationReply reply = replyJPARepository.findById(replyId).get();

        assertEquals(reply.getStatus(), 2);

    }

    @Test
    @Transactional
    @DisplayName("댓글 저장")
    public void saveTest(){

        long boardId = 1;
        long parentId = 3;
        long userId = 4;
        String writer = "작성자";
        String content = "내용";

        CommunicationReply updatedReply = CommunicationReply.builder()
                .boardId(boardId)
                .parentId(parentId)
                .userId(userId)
                .writer(writer)
                .content(content)
                .build();

        replyService.save(updatedReply);

        List<CommunicationReply> list = replyJPARepository.findAll();

        CommunicationReply reply = list.get(list.size()-1);

        assertEquals(reply.getBoardId(), boardId);
        assertEquals(reply.getUserId(), userId);
        assertEquals(reply.getWriter(), writer);

    }

    @Test
    @Transactional
    @DisplayName("댓글 수정")
    public void updateTest(){


        long id = 4;
        String content = "내용";

        CommunicationReply targetReply = replyJPARepository.findById(id).get();

        CommunicationReply updatedReply = CommunicationReply.builder()
                .id(targetReply.getId())
                .boardId(targetReply.getBoardId())
                .parentId(targetReply.getParentId())
                .userId(targetReply.getUserId())
                .writer(targetReply.getWriter())
                .content(content)
                .replyWriteDate(targetReply.getReplyWriteDate())
                .replyUpdateDate(LocalDateTime.now())
                .status(targetReply.getStatus())
                .build();

        replyService.save(updatedReply);

        CommunicationReply reply = replyJPARepository.findById(id).get();

        assertEquals(reply.getContent(), content);
        assertEquals(reply.getWriter(), targetReply.getWriter());

    }

    @Test
    @Transactional
    @DisplayName("신고목록 가져오기")
    public void findAllReportedReplyTest(){

        List<ReportedReply> list = replyService.findAllReportedReply();

        assertEquals(list.size(), 5);
    }

    @Test
    @Transactional
    @DisplayName("신고 저장")
    public void saveReportedReplyTest(){

        long replyId = 1;
        long reportedUserId = 2;
        long reporterUserId = 3;
        String reason = "테스트용";

        ReportedReply reportedReply = ReportedReply.builder()
                .replyId(replyId)
                .reportedUserId(reportedUserId)
                .reporterUserId(reporterUserId)
                .reason(reason)
                .build();

        replyService.saveReportedReply(reportedReply);

        List<ReportedReply> list = reportedReplyJPARepository.findAll();

        ReportedReply report = list.get(list.size()-1);

        assertEquals(report.getReporterUserId(), reporterUserId);
        assertEquals(report.getReason(), reason);

    }

    @Test
    @Transactional
    @DisplayName("신고 거절")
    public void rejectReportedReplyTest(){
        long id = 3;
        replyService.rejectReportedReply(id);

        ReportedReply reportedReply = reportedReplyJPARepository.findById(id).orElse(null);

        assertNull(reportedReply);
    }

    @Test
    @Transactional
    @DisplayName("신고 승인")
    public void approveReportedReplyTest(){
        long id = 3;
        replyService.approveReportedReply(id);

        CommunicationReply reply = replyJPARepository.findById(1L).orElse(null);
        ReportedReply reportedReply = reportedReplyJPARepository.findById(id).orElse(null);

        assertEquals(reply.getStatus(), 2);
        assertNull(reportedReply);
    }






}
