package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.BoardUpdateDTO;
import com.goalddae.dto.board.HeartInfoDTO;
import com.goalddae.dto.board.MyBoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationHeart;
import com.goalddae.entity.ReportedBoard;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {

    Page<BoardListDTO> findAllBoardListDTOs(Integer page, String type, String name);

    CommunicationBoard findById(Long boardId);

    void deleteById(Long boardId);

    void save(CommunicationBoard communicationBoard);

    void update(BoardUpdateDTO boardUpdateDTO);

    void viewCountUp(long boardId);

    HeartInfoDTO getHeartInfo(long boardId, long userId);

    void heartSave(long boardId, long userId);

    void heartDelete(long boardId, long userId);

    List<ReportedBoard> findAllReportedBoard();

    void saveReportedBoard(ReportedBoard reportedBoard);

    void rejectReportedBoard(long reportId);

    void approveReportedBoard(long reportId);

    List<MyBoardListDTO> getUserCommunicationBoardPosts(long userId);





}
