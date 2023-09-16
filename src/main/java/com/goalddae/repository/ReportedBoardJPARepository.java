package com.goalddae.repository;

import com.goalddae.entity.ReportedBoard;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface ReportedBoardJPARepository extends JpaRepository<ReportedBoard, Long> {

    Optional<ReportedBoard> findByBoardIdAndReporterUserId(long boardId, long reporterUserId);

    @Modifying
    @Transactional
    void deleteByBoardId(long boardId);
}
