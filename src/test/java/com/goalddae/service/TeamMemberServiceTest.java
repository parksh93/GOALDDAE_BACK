package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.user.ChangeUserInfoDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TeamMemberServiceTest {

    @Autowired
    private TeamMemberService teamMemberService;

    @Test
    @Transactional
    @DisplayName("")
    public void findTeamManagerByUserIdTest(){
        //given
        long teamId = 3;
        long userId = 2;
        int teamManager = 0;

        //when
        int tM = teamMemberService.findTeamManagerByUserId(userId, teamId);

        //then
        assertEquals(teamManager, tM);
    }


    @Test
    @Transactional
    @DisplayName("teamId=1 테이블에서 userId=7 삭제 시, 팀 멤버 사이즈 1")
    public void removeTeamMemberTest(){
        //given
        long userId = 7;
        long teamId = 1;

        GetUserInfoDTO getUserInfoDTO = GetUserInfoDTO.builder()
                .id(userId)
                .teamId(teamId)
                .build();

        //when
        teamMemberService.removeTeamMember(userId,teamId,getUserInfoDTO);

        List<TeamMemberCheckDTO> result = teamMemberService.findAllTeamMembersByTeamId(teamId);


        //then
        assertEquals(1,result.size());
        assertNull(teamMemberService.findByUserId(userId, teamId));
        assertEquals(0,getUserInfoDTO.getTeamId());
    }


}
