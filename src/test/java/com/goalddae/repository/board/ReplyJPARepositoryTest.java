package com.goalddae.repository.board;

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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ReplyJPARepositoryTest {

    @Autowired
    ReplyJPARepository replyJPARepository;

    @Autowired
    ReportedReplyJPARepository reportedReplyJPARepository;

    @Test
    @Transactional
    @DisplayName("boardId를 기준으로 전체 댓글 조회")
    public void findAllByBoardIdTest(){

        long boardId = 1;
        int index = 4;
        List<CommunicationReply> allReplies = replyJPARepository.findByBoardId(boardId);

        assertEquals(allReplies.size(), 11);
        assertEquals(allReplies.get(index).getId(), index+1);

    }

    @Test
    @Transactional
    @DisplayName("id를 기준으로 댓글 조회")
    public void findByIdTest(){

        long replyId = 4;
        String writer = "댓글작성자";
        CommunicationReply reply = replyJPARepository.findById(replyId).get();

        assertEquals(reply.getId(), replyId);
        assertEquals(reply.getWriter(), writer);

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
//                .userId(userId)
                .writer(writer)
                .content(content)
                .build();

        replyJPARepository.save(updatedReply);

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

        replyJPARepository.save(updatedReply);

        CommunicationReply reply = replyJPARepository.findById(id).get();

        assertEquals(reply.getContent(), content);
        assertEquals(reply.getWriter(), targetReply.getWriter());

    }

    @Test
    @Transactional
    @DisplayName("신고목록 가져오기")
    public void findAllReportedReplyTest(){
        List<ReportedReply> list = reportedReplyJPARepository.findAll();

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

        reportedReplyJPARepository.save(reportedReply);

        List<ReportedReply> list = reportedReplyJPARepository.findAll();

        ReportedReply report = list.get(list.size()-1);

        assertEquals(report.getReporterUserId(), reporterUserId);
        assertEquals(report.getReason(), reason);

    }

    @Test
    @Transactional
    @DisplayName("신고 삭제")
    public void deleteReportedReplyTest(){
        long id = 3;
        reportedReplyJPARepository.deleteById(id);

        ReportedReply reportedReply = reportedReplyJPARepository.findById(id).orElse(null);

        assertNull(reportedReply);
    }

    @Test
    @Transactional
    @DisplayName("")
    public void findTest(){
        List<Long> boardIds = Arrays.asList(1L, 2L, 3L);

        List<Object[]> result = replyJPARepository.countRepliesByBoardIds(boardIds);

        System.out.println(Arrays.toString(result.get(1)));
    }

    @Test
    @Transactional
    @DisplayName("신고 댓글 조회")
    public void findReportReply() {

    }
}
