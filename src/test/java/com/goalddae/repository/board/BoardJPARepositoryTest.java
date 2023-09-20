package com.goalddae.repository.board;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.admin.GetReportBoardDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationHeart;
import com.goalddae.entity.ReportedBoard;
import com.goalddae.repository.BoardJPARepository;
import com.goalddae.repository.HeartJPARepository;
import com.goalddae.repository.ReportedBoardJPARepository;
import com.goalddae.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardJPARepositoryTest {

    @Autowired
    BoardJPARepository boardJPARepository;

    @Autowired
    HeartJPARepository heartJPARepository;

    @Autowired
    ReportedBoardJPARepository reportedBoardJPARepository;

    @Test
    @Transactional
    @DisplayName("페이징처리된 게시글 목록 조회")
    public void findAllBoardListDTOTest(){

        int page = 7;
        final int PAGE_SIZE = 10;

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("id").descending());
        Page<BoardListDTO> list = boardJPARepository.findAllBoardListDTO(pageable);

        assertEquals(list.getTotalPages(), 20);
        assertEquals(list.getNumber(), page);


    }

    @Test
    @Transactional
    @DisplayName("boardId를 이용해 해당 게시글 조회")
    public void findByIdTest(){

        long boardId = 3;

        CommunicationBoard result = boardJPARepository.findById(boardId).get();

        assertEquals(result.getId(), boardId);
        assertEquals(result.getContent(), "내용3");


    }

    @Test
    @Transactional
    @DisplayName("id를 이용해 해당 게시글 삭제")
    public void deleteByIdTest(){

        long boardId = 5;

        boardJPARepository.deleteById(boardId);

        CommunicationBoard result = boardJPARepository.findById(boardId).orElse(null);

        assertNull(result);
        assertEquals(boardJPARepository.count(), 198);


    }

    @Test
    @Transactional
    @DisplayName("객체를 이용해 게시글 추가")
    public void saveTest(){

        long userId = 1;
        String writer = "테스트닉네임";
        String title = "테스트제목";
        String content = "테스트내용";


        CommunicationBoard newPost = CommunicationBoard.builder()
//                .userId(userId)
                .writer(writer)
                .title(title)
                .content(content)
                .img1(null)
                .img2(null)
                .img3(null)
                .img4(null)
                .img5(null)
                .boardSortation(1)
                .build();

        boardJPARepository.save(newPost);

        List<CommunicationBoard> list = boardJPARepository.findAll();

        CommunicationBoard result = list.get(list.size()-1);

        assertEquals(result.getUserId(), userId);
        assertEquals(result.getWriter(), writer);
        assertEquals(result.getTitle(), title);
        assertEquals(result.getContent(), content);
        assertNull(result.getImg1());


    }

    @Test
    @Transactional
    @DisplayName("객체를 이용해 게시글 수정")
    public void updateTest(){

        long id = 7;
        String title = "테스트제목";
        String content = "테스트내용";

        CommunicationBoard post = boardJPARepository.findById(id).get();


        post = CommunicationBoard.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .writer(post.getWriter())
                .title(title)
                .content(content)
                .img1(null)
                .img2(null)
                .img3(null)
                .img4(null)
                .img5(null)
                .boardSortation(1)
                .updateDate(LocalDateTime.now())
                .writeDate(post.getWriteDate())
                .build();

        boardJPARepository.save(post);

        CommunicationBoard result = boardJPARepository.findById(id).get();

        assertEquals(result.getTitle(), title);
        assertEquals(result.getContent(), content);
        assertNull(result.getImg1());


    }

    @Test
    @Transactional
    @DisplayName("id를 이용해 게시글 조회수 증가")
    public void viewCountUpTest(){

        long boardId = 5;

        CommunicationBoard communicationBoard = boardJPARepository.findById(boardId).get();

        long count1 = communicationBoard.getCount();

        communicationBoard.viewCountUp();

        assertEquals(communicationBoard.getCount(), count1+1);

    }

    @Test
    @Transactional
    @DisplayName("좋아요 정보 반환")
    public void getHeartInfo(){

        long boardId = 1;
        long userId = 1;

        List<CommunicationHeart> communicationHeartList = heartJPARepository.findByBoardId(boardId);

        boolean containsUserId = false;

        for (CommunicationHeart communicationHeart : communicationHeartList) {
            if (communicationHeart.getUserId() == userId) {
                containsUserId = true;
                break;
            }
        }

        assertEquals(communicationHeartList.size(), 1);
        assertTrue(containsUserId);

    }

    @Test
    @Transactional
    @DisplayName("좋아요 저장")
    public void heartSaveTest(){

        long boardId = 1;
        long userId = 2;

        Optional<CommunicationHeart> communicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);
        if(communicationHeart.isEmpty()){
            CommunicationHeart newHeart = CommunicationHeart.builder()
                    .boardId(boardId)
                    .userId(userId)
                    .build();
            heartJPARepository.save(newHeart);
        }

        Optional<CommunicationHeart> newCommunicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);

        assertEquals(newCommunicationHeart.get().getUserId(), userId);
        assertTrue(newCommunicationHeart.isPresent());

    }

    @Test
    @Transactional
    @DisplayName("좋아요 삭제")
    public void heartDeleteTest(){

        long boardId = 1;
        long userId = 1;

        Optional<CommunicationHeart> communicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);
        if(communicationHeart.isPresent()){
            heartJPARepository.delete(communicationHeart.get());
        }

        Optional<CommunicationHeart> newCommunicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);

        assertTrue(newCommunicationHeart.isEmpty());

    }

    @Test
    @Transactional
    @DisplayName("전체 신고목록 반환")
    public void findAllReportedBoardTest(){


        List<ReportedBoard> list = reportedBoardJPARepository.findAll();

        assertEquals(list.size(), 4);

    }

    @Test
    @Transactional
    @DisplayName("새 신고 추가")
    public void saveReportedBoardTest(){

        long boardId = 2;
        long reporterUserId = 4;
        long reportedUserId = 3;

        ReportedBoard reportedBoard = ReportedBoard.builder()
                .boardId(boardId)
                .reporterUserId(reporterUserId)
                .reportedUserId(reportedUserId)
                .build();


        reportedBoardJPARepository.save(reportedBoard);

        List<ReportedBoard> list = reportedBoardJPARepository.findAll();
        ReportedBoard result = list.get(list.size()-1);

        assertEquals(result.getBoardId(), boardId);
        assertEquals(result.getReporterUserId(), reporterUserId);
        assertEquals(result.getReportedUserId(), reportedUserId);

    }

    @Test
    @Transactional
    @DisplayName("신고 거절")
    public void rejectReportedBoardTest(){

        long reportId = 2;


        reportedBoardJPARepository.deleteById(reportId);

        ReportedBoard result = reportedBoardJPARepository.findById(reportId).orElse(null);

        assertNull(result);

    }

    @Test
    @Transactional
    @DisplayName("신고 승인")
    public void approveReportedBoardTest(){

        long reportId = 3;


        ReportedBoard reportedBoard = reportedBoardJPARepository.findById(reportId).get();

        long boardId = reportedBoard.getBoardId();



        boardJPARepository.deleteById(boardId);
        reportedBoardJPARepository.delete(reportedBoard);

        ReportedBoard result = reportedBoardJPARepository.findById(reportId).orElse(null);
        CommunicationBoard communicationBoard = boardJPARepository.findById(boardId).orElse(null);

        assertNull(result);
        assertNull(communicationBoard);
    }

    @Test
    @Transactional
    @DisplayName("금일 게시글 중 조회수가 많은 객체 조회")
    public void findTop5BoardTest() {
        // Given
        for (int i = 0; i < 5; i++) {
            CommunicationBoard board = CommunicationBoard.builder()
                    .id(1L)
                    .writer("writer" + i)
                    .title("title" + i)
                    .content("테스트" + i)
                    .writeDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .boardSortation(i % 2)
                    .count(100L - i)
                    .build();

            boardJPARepository.save(board);
        }

        // When
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<BoardListDTO> top5Boards = boardJPARepository.findTop5ByOrderByCountDesc(pageRequest);

        // Then
        assertEquals(5, top5Boards.size());

        for (int i = 0; i < top5Boards.size() - 1; i++) {
            assertTrue(top5Boards.get(i).getCount() >= top5Boards.get(i + 1).getCount());
            assertEquals(LocalDate.now(), top5Boards.get(i).getWriteDate().toLocalDate());
        }
    }
<<<<<<< HEAD
=======

    @Test
    @Transactional
    @DisplayName("신고 당한 게시글 조회")
    public void findReportBoard() {
        List<GetReportBoardDTO> reportBoardList = boardJPARepository.findReportBoard();

        assertEquals(1, reportBoardList.size());
        assertEquals("그냥", reportBoardList.get(0).getReason());
    }
>>>>>>> 6911fdba8fe07a53d5e0b0be953110a5f6398cfc
}
