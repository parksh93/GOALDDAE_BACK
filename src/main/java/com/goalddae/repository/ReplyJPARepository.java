package com.goalddae.repository;

import com.goalddae.dto.admin.GetReportReplyDTO;
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

    @Query("SELECT r.boardId, COUNT(r) FROM CommunicationReply r WHERE r.boardId IN :boardIds AND r.status = 0 GROUP BY r.boardId")
    List<Object[]> countRepliesByBoardIds(@Param("boardIds") List<Long> boardIds);

    @Query(value = "SELECT c.id, c.board_id AS boardId, c.content, c.writer, c.reply_write_date AS replyWriteDate, r.reported_date AS reportDate, r.reason, u.nickname AS reporter,b.title FROM communication_reply c JOIN reported_reply r ON c.id = r.reply_id JOIN users u ON u.id = r.reporter_user_id JOIN communication_board b ON c.board_id = b.id", nativeQuery = true)
    List<GetReportReplyDTO> findReportReply();
}
