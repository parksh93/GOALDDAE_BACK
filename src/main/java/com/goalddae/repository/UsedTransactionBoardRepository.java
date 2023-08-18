package com.goalddae.repository;

import com.goalddae.entity.UsedTransactionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsedTransactionBoardRepository extends JpaRepository<UsedTransactionBoard, Long> {
    List<UsedTransactionBoard> findPostById(long userId);
}
