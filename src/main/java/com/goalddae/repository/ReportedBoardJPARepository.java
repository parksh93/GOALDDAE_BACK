package com.goalddae.repository;

import com.goalddae.entity.ReportedBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedBoardJPARepository extends JpaRepository<ReportedBoard, Long> {
}
