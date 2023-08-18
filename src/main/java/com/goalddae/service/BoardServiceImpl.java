package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.BoardUpdateDTO;
import com.goalddae.dto.board.HeartInfoDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationHeart;
import com.goalddae.entity.ReportedBoard;
import com.goalddae.repository.BoardJPARepository;
import com.goalddae.repository.HeartJPARepository;
import com.goalddae.repository.ReportedBoardJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService{

    BoardJPARepository boardJPARepository;
    HeartJPARepository heartJPARepository;
    ReportedBoardJPARepository reportedBoardJPARepository;

    @Autowired
    public BoardServiceImpl(BoardJPARepository boardJPARepository, HeartJPARepository heartJPARepository, ReportedBoardJPARepository reportedBoardJPARepository){
        this.boardJPARepository = boardJPARepository;
        this.heartJPARepository = heartJPARepository;
        this.reportedBoardJPARepository = reportedBoardJPARepository;
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

        boardJPARepository.save(communicationBoard);
    }


    @Override
    public void update(BoardUpdateDTO boardUpdateDTO) {

        CommunicationBoard updatedPost = boardJPARepository.findById(boardUpdateDTO.getId()).get();

        System.out.println(updatedPost.getId());

        updatedPost = CommunicationBoard.builder()
                .id(updatedPost.getId())
                .userId(updatedPost.getUserId())
                .writer(updatedPost.getWriter())
                .title(boardUpdateDTO.getTitle())
                .content(boardUpdateDTO.getContent())
                .img1(boardUpdateDTO.getImg1())
                .img2(boardUpdateDTO.getImg2())
                .img3(boardUpdateDTO.getImg3())
                .img4(boardUpdateDTO.getImg4())
                .img5(boardUpdateDTO.getImg5())
                .boardSortation(updatedPost.getBoardSortation())
                .updateDate(LocalDateTime.now())
                .writeDate(updatedPost.getWriteDate())
                .count(updatedPost.getCount())
                .build();

            boardJPARepository.save(updatedPost);

    }

    @Transactional
    @Override
    public void viewCountUp(long boardId) {
        CommunicationBoard communicationBoard = boardJPARepository.findById(boardId).get();
        communicationBoard.viewCountUp();
    }

    @Override
    public HeartInfoDTO getHeartInfo(long boardId, long userId) {
        List<CommunicationHeart> communicationHeartList = heartJPARepository.findByBoardId(boardId);

        boolean containsUserId = false;

        for (CommunicationHeart communicationHeart : communicationHeartList) {
            if (communicationHeart.getUserId() == userId) {
                containsUserId = true;
                break;
            }
        }

        return new HeartInfoDTO(communicationHeartList.size(), containsUserId);
    }

    @Override
    public void heartSave(long boardId, long userId) {
        Optional<CommunicationHeart> communicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);
        if(communicationHeart.isEmpty()){
            CommunicationHeart newHeart = CommunicationHeart.builder()
                    .boardId(boardId)
                    .userId(userId)
                    .build();
            heartJPARepository.save(newHeart);
        }
    }

    @Override
    public void heartDelete(long boardId, long userId) {
        Optional<CommunicationHeart> communicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);
        if(communicationHeart.isPresent()){
            heartJPARepository.delete(communicationHeart.get());
        }
    }

    @Override
    public List<ReportedBoard> findAllReportedBoard() {
        return reportedBoardJPARepository.findAll();
    }

    @Transactional
    @Override
    public void saveReportedBoard(ReportedBoard reportedBoard) {
        reportedBoardJPARepository.save(reportedBoard);
    }

    @Transactional
    @Override
    public void rejectReportedBoard(long reportId) {
        reportedBoardJPARepository.deleteById(reportId);
    }

    @Transactional
    @Override
    public void approveReportedBoard(long reportId) {
        ReportedBoard reportedBoard = reportedBoardJPARepository.findById(reportId).get();

        boardJPARepository.deleteById(reportedBoard.getBoardId());
        reportedBoardJPARepository.delete(reportedBoard);
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
