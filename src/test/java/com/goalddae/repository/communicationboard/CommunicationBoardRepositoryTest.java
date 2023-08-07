package com.goalddae.repository.communicationboard;

import com.goalddae.repository.CommunicationBoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CommunicationBoardRepositoryTest {

    @Autowired
    CommunicationBoardRepository communicationBoardRepository;

    @Test
    @Transactional
    @DisplayName("id를 통한 유저가 쓴 글 조회")
    public void findByWriterTest() {

    }
}
