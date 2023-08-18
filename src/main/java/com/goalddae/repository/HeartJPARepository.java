package com.goalddae.repository;

import com.goalddae.entity.CommunicationHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HeartJPARepository extends JpaRepository<CommunicationHeart, Long> {

    List<CommunicationHeart> findByBoardId(long boardId);

    Optional<CommunicationHeart> findByBoardIdAndUserId(long boardId, long userId);
}
