package com.goalddae.repository;

import com.goalddae.entity.ReportedBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportedBoardJPARepository extends JpaRepository<ReportedBoard, Long> {

    Optional<ReportedBoard> findByBoardIdAndReporterUserId(long boardId, long reporterUserId);
}
