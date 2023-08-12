package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.repository.BoardJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoardServiceImpl implements BoardService{

    BoardJPARepository boardJPARepository;

    @Autowired
    public BoardServiceImpl(BoardJPARepository boardJPARepository){
        this.boardJPARepository = boardJPARepository;
    }

    @Override
    public Page<BoardListDTO> findAllBoardListDTO(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(getCalibratedPno(page), size, Sort.by("id").descending());
        return boardJPARepository.findAllBoardListDTO(pageable);
    }

    @Override
    public CommunicationBoard findById(Long boardId) {
        return boardJPARepository.findById(boardId).orElse(null);
    }

    @Override
    public void deleteById(Long boardId) {
        boardJPARepository.deleteById(boardId);
    }

    @Override
    public void save(CommunicationBoard communicationBoard) {
        CommunicationBoard newPost = CommunicationBoard.builder()
                .userId(communicationBoard.getUserId())
                .writer(communicationBoard.getWriter())
                .title(communicationBoard.getTitle())
                .content(communicationBoard.getContent())
                .img1(communicationBoard.getImg1())
                .img2(communicationBoard.getImg2())
                .img3(communicationBoard.getImg3())
                .img4(communicationBoard.getImg4())
                .img5(communicationBoard.getImg5())
                .boardSortation(communicationBoard.getBoardSortation())
                .build();

        boardJPARepository.save(newPost);
    }


    @Override
    public void update(CommunicationBoard communicationBoard) {

        CommunicationBoard updatedPost = boardJPARepository.findById(communicationBoard.getId()).get();

        System.out.println(updatedPost.getId());

        updatedPost = CommunicationBoard.builder()
                .id(communicationBoard.getId())
                .userId(communicationBoard.getUserId())
                .writer(communicationBoard.getWriter())
                .title(communicationBoard.getTitle())
                .content(communicationBoard.getContent())
                .img1(communicationBoard.getImg1())
                .img2(communicationBoard.getImg2())
                .img3(communicationBoard.getImg3())
                .img4(communicationBoard.getImg4())
                .img5(communicationBoard.getImg5())
                .boardSortation(communicationBoard.getBoardSortation())
                .updateDate(LocalDateTime.now())
                .writeDate(updatedPost.getWriteDate())
                .build();

            boardJPARepository.save(updatedPost);

    }


    public int getCalibratedPno(Integer pno){
        long count = boardJPARepository.count();
        if (pno <= 0 || count == 0) {
            return 0;
        }
        int page = (int) Math.ceil(count / 10.0);
        pno = pno > page ? page : pno;
        return pno - 1;
    }
}
