package com.goalddae.repository.report;

import com.goalddae.repository.ReportedBoardJPARepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ReportBoardRepositoryTest {
    @Autowired
    private ReportedBoardJPARepository reportedBoardJPARepository;

    @Test
    @Transactional
    @DisplayName("신고 목록 삭제")
    public void deleteByBoardId() {
        long boardId = 3;

        reportedBoardJPARepository.deleteByBoardId(boardId);
    }
}
