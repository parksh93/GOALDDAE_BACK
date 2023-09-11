package com.goalddae.repository.team;


import com.goalddae.dto.team.TeamApplyDTO;
import com.goalddae.repository.TeamApplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamApplyRepositoryTest {

    @Autowired
    TeamApplyRepository teamApplyRepository;

    @Test
    @Transactional
    @DisplayName("teamAcceptStatus=1, 사이즈=1")
    public void addTeamApplyTest(){
        //given
        long teamId = 4;
        long userId = 1;

        TeamApplyDTO newApply = TeamApplyDTO.builder()
                .teamId(teamId)
                .teamAcceptStatus(1)
                .userId(userId)
                .build();

        //when
        teamApplyRepository.addTeamApply(newApply);

        //then
        assertEquals(1, newApply.getTeamAcceptStatus());
        assertEquals(1, newApply.getUserId());
        assertEquals(4, newApply.getTeamId());
    }

}
