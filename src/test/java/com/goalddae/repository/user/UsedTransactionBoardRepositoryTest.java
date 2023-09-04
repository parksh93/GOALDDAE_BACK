package com.goalddae.repository.user;

import com.goalddae.entity.CommunicationBoard;
import com.goalddae.entity.UsedTransactionBoard;
import com.goalddae.repository.UsedTransactionBoardRepository;
import com.goalddae.service.UserDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsedTransactionBoardRepositoryTest {
    @Autowired
    UsedTransactionBoardRepository usedTransactionBoardRepository;
    @Test
    @Transactional
    @DisplayName("자유게시판에 쓴 글 조회 테스트")
    public void findPostByIdTest(){
        // given
<<<<<<< HEAD
        long userId = 1;
=======
        long userId = 123;
>>>>>>> develop

        // when
        List<UsedTransactionBoard>usedTransactionBoardList = usedTransactionBoardRepository.findByUserId(userId);

        // then
        assertEquals(0, usedTransactionBoardList.size());
    }
}
