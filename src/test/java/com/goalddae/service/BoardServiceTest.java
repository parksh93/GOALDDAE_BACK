package com.goalddae.service;

import com.goalddae.dto.board.*;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.CommunicationHeart;
import com.goalddae.entity.CommunicationReply;
import com.goalddae.entity.ReportedBoard;
import com.goalddae.repository.BoardJPARepository;
import com.goalddae.repository.HeartJPARepository;
import com.goalddae.repository.ReplyJPARepository;
import com.goalddae.repository.ReportedBoardJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardJPARepository boardJPARepository;

    @Autowired
    HeartJPARepository heartJPARepository;

    @Autowired
    ReportedBoardJPARepository reportedBoardJPARepository;

    @Autowired
    ReplyJPARepository replyJPARepository;

    @Test
    @Transactional
    @DisplayName("페이징처리된 게시글 목록 조회")
    public void findAllBoardListDTOTest(){

    }

    @Test
    @Transactional
    @DisplayName("boardId를 이용해 해당 게시글 조회")
    public void findByIdTest(){

        long boardId = 3;

        CommunicationBoard result = boardService.findById(boardId);

        assertEquals(result.getId(), boardId);
        assertEquals(result.getContent(), "내용3");


    }

    @Test
    @Transactional
    @DisplayName("id를 이용해 해당 게시글 삭제")
    public void deleteByIdTest(){

        long boardId = 5;

        boardService.deleteById(boardId);

        assertNull(boardService.findById(boardId));
        assertEquals(boardJPARepository.count(), 199);


    }

    @Test
    @Transactional
    @DisplayName("객체를 이용해 게시글 추가")
    public void saveTest(){

        long userId = 7;
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

        boardService.save(newPost);

        List<CommunicationBoard> list = boardJPARepository.findAll();
        long index = list.get(list.size()-1).getId();

        CommunicationBoard result = boardService.findById(index);

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


        BoardUpdateDTO boardUpdateDTO = BoardUpdateDTO.builder()
                .id(id)
//                .userId(userId)
                .title(title)
                .content(content)
                .img1(null)
                .img2(null)
                .img3(null)
                .img4(null)
                .img5(null)
                .build();

        boardService.update(boardUpdateDTO);

        CommunicationBoard result = boardService.findById(id);

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

        boardService.viewCountUp(boardId);

        assertEquals(communicationBoard.getCount(), count1+1);

    }

    @Test
    @Transactional
    @DisplayName("좋아요 정보 반환")
    public void getHeartInfo(){

        long boardId = 1;
        long userId = 1;

        HeartInfoDTO heartInfoDTO = boardService.getHeartInfo(boardId, userId);

        assertEquals(heartInfoDTO.getHeartCount(), 1);
        assertTrue(heartInfoDTO.isHearted());

    }

    @Test
    @Transactional
    @DisplayName("좋아요 저장")
    public void heartSaveTest(){

        long boardId = 1;
        long userId = 2;

        boardService.heartSave(boardId, userId);

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

        boardService.heartDelete(boardId, userId);

        Optional<CommunicationHeart> newCommunicationHeart = heartJPARepository.findByBoardIdAndUserId(boardId, userId);

        assertTrue(newCommunicationHeart.isEmpty());

    }

    @Test
    @Transactional
    @DisplayName("전체 신고목록 반환")
    public void findAllReportedBoardTest(){


        List<ReportedBoard> list = boardService.findAllReportedBoard();

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


        boardService.saveReportedBoard(reportedBoard);

        List<ReportedBoard> list = boardService.findAllReportedBoard();
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


        boardService.rejectReportedBoard(reportId);

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

        boardService.approveReportedBoard(reportId);

        ReportedBoard result = reportedBoardJPARepository.findById(reportId).orElse(null);
        CommunicationBoard communicationBoard = boardJPARepository.findById(boardId).orElse(null);

        assertNull(result);
        assertNull(communicationBoard);

    }

    @Test
    @Transactional
    @DisplayName("해당 유저의 게시글 리스트 조회하기")
    public void getUserCommunicationBoardPostsTest() {
        // given
        long userId = 2;

        // when
        List<MyBoardListDTO> myBoardList = boardService.getUserCommunicationBoardPosts(userId);

        // then
        assertEquals(11, myBoardList.size());
    }

    @DisplayName("조회수가 가장 많은 객체 조회 테스트")
    public void findTop5BoardTest() {
        // Given
        for (int i = 0; i < 10; i++) {
            CommunicationBoard board = CommunicationBoard.builder()
                    .userId((long)i)
                    .writer("writer" + i)
                    .title("title" + i)
                    .content("content" + i)
                    .boardSortation(i % 2)
                    .count(100L - i)
                    .build();

            boardJPARepository.save(board);
        }

        // When
        List<BoardListDTO> top5Boards = boardService.findTop5Board();

        // Then
        assertEquals(5, top5Boards.size());

        for (int i = 0; i < top5Boards.size() - 1; i++) {
            assertTrue(top5Boards.get(i).getCount() >= top5Boards.get(i + 1).getCount());
            assertEquals(LocalDate.now(), top5Boards.get(i).getWriteDate().toLocalDate());
        }
    }
}
