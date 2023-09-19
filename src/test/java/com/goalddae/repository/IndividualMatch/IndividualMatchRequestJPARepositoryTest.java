package com.goalddae.repository.IndividualMatch;

import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.repository.IndividualMatchRequestJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
public class IndividualMatchRequestJPARepositoryTest {
    @Autowired
    private IndividualMatchRequestJPARepository individualMatchRequestJPARepository;

    @Test
    @DisplayName("개인매치 신청내역 조회 테스트")
    public void findAllByUserIdTest() {
        // given
        long userId = 2;

        // when
        List<IndividualMatchRequest> individualMatchRequestList = individualMatchRequestJPARepository.findAllByUserId(userId);

        // then
        assertEquals("여자", individualMatchRequestList.get(0).getIndividualMatch().getGender());
        assertEquals(2, individualMatchRequestList.get(0).getIndividualMatch().getReservationField().getId());
    }
}
