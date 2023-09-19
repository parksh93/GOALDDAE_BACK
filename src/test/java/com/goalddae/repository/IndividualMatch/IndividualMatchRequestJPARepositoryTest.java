package com.goalddae.repository.IndividualMatch;

import com.goalddae.dto.match.GetPlayerInfoDTO;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.User;
import com.goalddae.repository.IndividualMatchRequestJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    @Transactional
    @DisplayName("경기 참가인원 조회")
    public void countByIndividualMatchId() {
        long matchId = 1;

        int playerNum = individualMatchRequestJPARepository.countByIndividualMatchId(matchId);

        assertEquals(1, playerNum);
    }

    @Test
    @Transactional
    @DisplayName("매치 신청자 목록 조회")
    public void findIndividualMatchPlayerListTest(){
        long matchId = 1;
        List<GetPlayerInfoDTO> userList = individualMatchRequestJPARepository.findIndividualMatchPlayerList(matchId);

        assertEquals(1, userList.size());
    }

    @Test
    @Transactional
    @DisplayName("매치 참가 취소")
    public void deleteByUserIdAndIndividualMatchIdTest() {
        long matchId = 1;
        long userId = 20;
        individualMatchRequestJPARepository.deleteByUserIdAndIndividualMatchId(userId, matchId);

        List<GetPlayerInfoDTO> userList = individualMatchRequestJPARepository.findIndividualMatchPlayerList(matchId);

        assertEquals(0, userList.size());
    }
}
