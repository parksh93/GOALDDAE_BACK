package com.goalddae.repository.report;

import com.goalddae.repository.ReportedReplyJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ReportReplyRepositoryTest {
    @Autowired
    ReportedReplyJPARepository replyJPARepository;

    @Test
    @Transactional
    @DisplayName("신고 내역 삭제")
    public void deleteByReplyIdTes(){
        long replyId = 2;

        replyJPARepository.deleteByReplyId(replyId);
    }
}
