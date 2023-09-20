package com.goalddae.service;

import com.goalddae.dto.board.*;
import com.goalddae.entity.CommunicationBoard;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

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

    List<MyBoardListDTO> getUserCommunicationBoardPosts(long userId);

    // 조회수 탑5 게시글 조회
    List<BoardListDTO> findTop5Board();

    String uploadImage (MultipartFile multipartFile);
}
