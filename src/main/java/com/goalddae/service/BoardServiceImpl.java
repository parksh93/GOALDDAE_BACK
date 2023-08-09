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
        return boardJPARepository.findById(boardId).get();
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
        
    }

//    @Override
//    public void update(CommunicationBoard communicationBoard) {
//        CommunicationBoard updatedPost = boardJPARepository.findById(communicationBoard.getId()).orElse(null);
//
//        if (updatedPost != null) {
//            updatedPost.setUserId(communicationBoard.getUserId());
//            updatedPost.setWriter(communicationBoard.getWriter());
//            updatedPost.setTitle(communicationBoard.getTitle());
//            updatedPost.setContent(communicationBoard.getContent());
//            updatedPost.setImg1(communicationBoard.getImg1());
//            updatedPost.setImg2(communicationBoard.getImg2());
//            updatedPost.setImg3(communicationBoard.getImg3());
//            updatedPost.setImg4(communicationBoard.getImg4());
//            updatedPost.setImg5(communicationBoard.getImg5());
//            updatedPost.setBoardSortation(communicationBoard.getBoardSortation());
//
//            boardJPARepository.save(updatedPost);
//        } else {
//            // 엔티티를 찾지 못한 경우 예외 처리 등을 수행할 수 있습니다.
//        }
//    }


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
