package com.goalddae.repository.user;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.User;
import com.goalddae.repository.CommunicationBoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CommunicationBoardRepositoryTest {
    @Autowired
    CommunicationBoardRepository communicationBoardRepository;
    @Test
    @Transactional
    @DisplayName("자유게시판에 쓴 글 조회 테스트")
    public void findPostByIdTest(){
        // given
        long userId = 1;

        // when
        List<CommunicationBoard> communicationBoardList = communicationBoardRepository.findByUserId(userId);

        // then
        assertEquals(0, communicationBoardList.size());
    }
}
