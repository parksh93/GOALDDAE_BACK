package com.goalddae.repository;

import com.goalddae.entity.CommunicationHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HeartJPARepository extends JpaRepository<CommunicationHeart, Long> {

    @Query("SELECT r.boardId, COUNT(r) FROM CommunicationHeart r WHERE r.boardId IN :boardIds GROUP BY r.boardId")
    List<Object[]> countHeartsByBoardIds(@Param("boardIds") List<Long> boardIds);

    List<CommunicationHeart> findByBoardId(long boardId);

    Optional<CommunicationHeart> findByBoardIdAndUserId(long boardId, long userId);
}
