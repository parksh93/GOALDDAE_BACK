package com.goalddae.repository;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardJPARepository extends JpaRepository<CommunicationBoard, Long> {
    @Query("SELECT new com.goalddae.dto.board.BoardListDTO(b.id, b.writer, b.title, b.writeDate, b.updateDate, b.img1, b.boardSortation, b.count, 0, 0) FROM CommunicationBoard b")
    Page<BoardListDTO> findAllBoardListDTO(Pageable pageable);

    @Query("SELECT new com.goalddae.dto.board.BoardListDTO(b.id, b.writer, b.title, b.writeDate, b.updateDate, b.img1, b.boardSortation, b.count, 0, 0) FROM CommunicationBoard b WHERE b.writer LIKE %:writer%")
    Page<BoardListDTO> findAllBoardListDTOByWriter(@Param("writer") String writer, Pageable pageable);

    @Query("SELECT new com.goalddae.dto.board.BoardListDTO(b.id, b.writer, b.title, b.writeDate, b.updateDate, b.img1, b.boardSortation, b.count, 0, 0) FROM CommunicationBoard b WHERE b.title LIKE %:title%")
    Page<BoardListDTO> findAllBoardListDTOByTitle(@Param("title") String title, Pageable pageable);

    // 금일 조회수 탑5 게시글 조회
    @Query("SELECT new com.goalddae.dto.board.BoardListDTO(b.id, b.writer, b.title, b.writeDate, b.updateDate, b.img1, b.boardSortation, b.count, 0, 0) FROM CommunicationBoard b WHERE FUNCTION('DATE',b.writeDate) = CURRENT_DATE ORDER BY b.count DESC")
    List<BoardListDTO> findTop5ByOrderByCountDesc(Pageable pageable);
}