package com.goalddae.repository.board;

import com.goalddae.dto.board.BoardListDTO;
import com.goalddae.entity.CommunicationBoard;
import com.goalddae.repository.BoardJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BoardJPARepositoryTest {

    @Autowired
    BoardJPARepository boardJPARepository;


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
        assertEquals(boardJPARepository.count(), 199);


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

        boardJPARepository.save(newPost);

        List<CommunicationBoard> list = boardJPARepository.findAll();
        long index = list.get(list.size()-1).getId();

        CommunicationBoard result = boardJPARepository.findById(index).get();

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
        long userId = 1;
        String writer = "테스트닉네임";
        String title = "테스트제목";
        String content = "테스트내용";

        CommunicationBoard post = boardJPARepository.findById(id).get();


        CommunicationBoard updatedPost = CommunicationBoard.builder()
                .id(id)
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
                .updateDate(LocalDateTime.now())
                .writeDate(post.getWriteDate())
                .build();

        boardJPARepository.save(updatedPost);

        CommunicationBoard result = boardJPARepository.findById(id).get();

        assertEquals(result.getUserId(), userId);
        assertEquals(result.getWriter(), writer);
        assertEquals(result.getTitle(), title);
        assertEquals(result.getContent(), content);
        assertNull(result.getImg1());


    }



}
