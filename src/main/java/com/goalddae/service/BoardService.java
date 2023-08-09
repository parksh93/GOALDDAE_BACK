package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import org.springframework.data.domain.Page;

public interface BoardService {
    Page<BoardListDTO> findAllBoardListDTO(Integer page, Integer Size);

    CommunicationBoard findById(Long boardId);

    void deleteById(Long boardId);

    void save(CommunicationBoard communicationBoard);

    void update(CommunicationBoard communicationBoard);




}
