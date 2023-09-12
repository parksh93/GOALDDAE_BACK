package com.goalddae.repository.team;

import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.repository.TeamMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamMemberRepositoryTest {

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Test
    @Transactional
    @DisplayName("teamId=3의 사이즈 1, userId=1 ")
    public void findAllTeamMembersByTeamIdTest() {
        //given
        long teamId = 3;
        long userId = 1;

        //when
        List<TeamMemberDTO> members = teamMemberRepository.findAllTeamMembersByTeamId(teamId);

        //then
        assertEquals(1, members.size());
        //assertEquals(teamId, members.get(0).getTeamId());
        assertEquals(userId, members.get(0).getUserId());
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


}