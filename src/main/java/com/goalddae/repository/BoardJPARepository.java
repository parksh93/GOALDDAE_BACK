package com.goalddae.repository;

import com.goalddae.entity.CommunicationBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJPARepository extends JpaRepository<CommunicationBoard, Long> {
}
