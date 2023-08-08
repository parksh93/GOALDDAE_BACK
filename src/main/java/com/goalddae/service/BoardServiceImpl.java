package com.goalddae.service;

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
    public Page<CommunicationBoard> findAll(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(getCalibratedPno(page), size, Sort.by("id").descending());
        return boardJPARepository.findAll(pageable);
    }

    @Override
    public CommunicationBoard findById(Long boardId) {
        return boardJPARepository.findById(boardId).get();
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
