package com.goalddae.service;

import com.goalddae.entity.SoccerField;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SoccerFieldServiceTest {

    @Autowired
    private SoccerFieldService soccerFieldService;

    @Test
    @Transactional
    @DisplayName("주어진 지역에 따라 축구장 목록 검색")
    public void searchFieldsByRegionTest() {
        String region = "성남";

        List<SoccerField> soccerFields = soccerFieldService.searchSoccerFields(region);

        // 검색된 축구장 목록에 해당 지역이 포함되어 있는지 확인
        assertTrue(soccerFields.stream().anyMatch(soccerField -> soccerField.getRegion().equals(region)));
    }

    @Test
    @Transactional
    @DisplayName("주어진 축구장 이름으로 검색")
    public void searchFieldsByFieldNameTest() {
        String fieldName = "야탑 풋살장";

        List<SoccerField> soccerFields = soccerFieldService.searchSoccerFields(fieldName);

        // 검색된 축구장 목록에 해당 축구장 이름이 포함되어 있는지 확인
        assertTrue(soccerFields.stream().anyMatch(soccerField -> soccerField.getFieldName().equals(fieldName)));
    }
}