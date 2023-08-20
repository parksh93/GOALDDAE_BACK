package com.goalddae.repository;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyJPARepository extends JpaRepository<CommunicationReply, Long> {

    List<CommunicationReply> findByBoardId(long boardId);

    @Query("SELECT r.boardId, COUNT(r) FROM CommunicationReply r WHERE r.boardId IN :boardIds GROUP BY r.boardId")
    List<Object[]> countRepliesByBoardIds(@Param("boardIds") List<Long> boardIds);
}
