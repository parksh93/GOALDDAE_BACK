package com.goalddae.repository.team;


import com.goalddae.dto.team.TeamApplyDTO;
import com.goalddae.repository.TeamApplyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamApplyRepositoryTest {

    @Autowired
    TeamApplyRepository teamApplyRepository;


    @Test
    @Transactional
    @DisplayName("teamId=3으로 확인 시 사이즈=1,userId= 2")
    public void findAllApplyByTeamIdTest(){
        //given
        long teamId = 3;
        long userId = 2;

        //when
        List<TeamApplyDTO> applys = teamApplyRepository.findAllApplyByTeamId(teamId);

        //then
        assertEquals(1, applys.size());
        assertEquals(userId, applys.get(0).getUserId());
    }

    @Test
    @Transactional
    @DisplayName("id=1 검색 시 userId=2")
    public void findApplyByIdTest(){
        //given
        long id = 1;
        long teamId = 3;
        long userId = 2;

        //when
        TeamApplyDTO apply = teamApplyRepository.findApplyById(id, teamId);

        //then
        assertEquals(userId, apply.getUserId());

    }


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

    @Test
    @Transactional
    @DisplayName("")
    public void updateAcceptStatusTest(){
        long id = 1;
        long teamId = 3;
        int teamAcceptStatus = 2;

        TeamApplyDTO apply = TeamApplyDTO.builder()
                .id(id)
                .teamId(teamId)
                .teamAcceptStatus(teamAcceptStatus)
                .build();

        teamApplyRepository.updateAcceptStatus(apply);

        assertEquals(teamAcceptStatus, apply.getTeamAcceptStatus());

    }
}
