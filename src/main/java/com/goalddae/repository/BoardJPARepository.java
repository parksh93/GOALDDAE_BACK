package com.goalddae.repository;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardJPARepository extends JpaRepository<CommunicationBoard, Long> {
    @Query("SELECT new com.goalddae.dto.board.BoardListDTO(b.id, b.writer, b.title, b.writeDate, b.updateDate, b.img1, b.boardSortation, b.count, 0, 0) FROM CommunicationBoard b")
    Page<BoardListDTO> findAllBoardListDTO(Pageable pageable);

    @Query("SELECT new com.goalddae.dto.board.BoardListDTO(b.id, b.writer, b.title, b.writeDate, b.updateDate, b.img1, b.boardSortation, b.count, 0, 0) FROM CommunicationBoard b WHERE b.writer LIKE %:writer%")
    Page<BoardListDTO> findAllBoardListDTOByWriter(@Param("writer") String writer, Pageable pageable);

    @Query("SELECT new com.goalddae.dto.board.BoardListDTO(b.id, b.writer, b.title, b.writeDate, b.updateDate, b.img1, b.boardSortation, b.count, 0, 0) FROM CommunicationBoard b WHERE b.title LIKE %:title%")
    Page<BoardListDTO> findAllBoardListDTOByTitle(@Param("title") String title, Pageable pageable);


}