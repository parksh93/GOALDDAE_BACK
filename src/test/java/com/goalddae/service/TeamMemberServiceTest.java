package com.goalddae.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


}
