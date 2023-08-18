package com.goalddae.service;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.dto.board.BoardUpdateDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.repository.BoardJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardJPARepository boardJPARepository;


    @Test
    @Transactional
    @DisplayName("페이징처리된 게시글 목록 조회")
    public void findAllBoardListDTOTest(){

        int page = 7;
        final int PAGE_SIZE = 10;

        Page<BoardListDTO> list = boardService.findAllBoardListDTO(page, PAGE_SIZE);

        assertEquals(list.getTotalPages(), 20);
        assertEquals(list.getNumber(), page - 1);


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
                .userId(userId)
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



}
