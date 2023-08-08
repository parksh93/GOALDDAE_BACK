package com.goalddae.repository;

import com.goalddae.entity.CommunicationBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunicationBoardRepository extends JpaRepository<CommunicationBoard, Long> {
    List<CommunicationBoard> findPostById(long id);
}