    package com.goalddae.repository.Search;

    import com.goalddae.entity.SoccerFiled;
    import com.goalddae.repository.SoccerFiledRepository;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.transaction.annotation.Transactional;

    import java.util.List;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

    @SpringBootTest
    public class SoccerFiledRepositoryTest {

        @Autowired
        SoccerFiledRepository soccerFiledRepository;

        @Test
        @Transactional
        @DisplayName("검색 기능을 이용한 구장 지역 조회")
        public void FindByRegionTest() {
            String keyword = "성남";

            List<SoccerFiled> soccerFiledList = soccerFiledRepository.findByRegion(keyword);

            assertNotNull(soccerFiledList);

            if (!soccerFiledList.isEmpty()) {
                assertEquals(keyword, soccerFiledList.get(0).getRegion());
                for (SoccerFiled soccerFiled : soccerFiledList) {
                    System.out.println("검색 결과: " + soccerFiled.getFiledName());
                }
            } else {
                System.out.println("검색 결과 없음");
            }
        }
    }