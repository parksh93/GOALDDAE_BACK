package com.goalddae.repository.soccerField;

import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SoccerFieldRepositoryTest {
    @Autowired
    private SoccerFieldRepository soccerFieldRepository;

    @Test
    @Transactional
    @DisplayName("구장 정보 가져오기")
    public void findByIdTest(){
        long id = 1;

        SoccerField soccerField = soccerFieldRepository.findById(id).get();

        assertEquals("테스트 구장", soccerField.getFieldName());
    }
}
