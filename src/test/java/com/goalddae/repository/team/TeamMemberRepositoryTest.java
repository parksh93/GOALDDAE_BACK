package com.goalddae.repository.team;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.repository.TeamMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.SimpleTimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TeamMemberRepositoryTest {

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Test
    @Transactional
    @DisplayName("teamId=3의 사이즈 1, userId=1 ")
    public void findAllTeamMembersByTeamIdTest() {
        //given
        long teamId = 1;
        long userId = 1;
        String name = "김재원";

        //when
        List<TeamMemberCheckDTO> members = teamMemberRepository.findAllTeamMembersByTeamId(teamId);

        //then
        assertEquals(2, members.size());
        assertEquals(userId, members.get(0).getUserId());
        assertEquals(name, members.get(0).getName());
    }

    @Test
    @Transactional
    @DisplayName("teamId=3, userId=2 검색 시  teamManager=0")
    public void findTeamManagerByUserIdTest(){
        //given
        long teamId = 3;
        long userId = 2;
        int teamManager = 0;

        //when
        int tM = teamMemberRepository.findTeamManagerByUserId(userId, teamId);

        //then
        assertEquals(teamManager, tM);

    }

    @Test
    @Transactional
    @DisplayName("새로운 팀 멤버의 teamId=4")
    public void addTeamMemberTest() {
        // given
        long teamId = 4;
        long userId = 5;

        TeamMemberDTO newTeamMember = TeamMemberDTO.builder()
                .teamId(teamId)
                .userId(userId)
                .build();

        //when
        teamMemberRepository.addTeamMember(newTeamMember);

        //then
        assertEquals(5, newTeamMember.getUserId());
    }

    @Test
    @Transactional
    @DisplayName("")
    public void deleteMemberByUserIdTest(){
        //given
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .userId(2)
                .teamId(2)
                .build();

        //when
        teamMemberRepository.deleteMemberByUserId(teamMemberDTO);
        List<TeamMemberCheckDTO> members = teamMemberRepository.findAllTeamMembersByTeamId(teamMemberDTO.getTeamId());

        //then
        assertEquals(0, members.size());
//        assertNull();
    }
}