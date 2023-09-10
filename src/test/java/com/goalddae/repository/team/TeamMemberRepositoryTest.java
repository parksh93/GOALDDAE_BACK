package com.goalddae.repository.team;

import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.repository.TeamMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TeamMemberRepositoryTest {

    @Autowired
    TeamMemberRepository teamMemberRepository;

    @Test
    @Transactional
    @DisplayName("")
    public void addTeamMemberTest(){
        // given
        long teamId = 3;
        long userId = 5;

        TeamMemberDTO newTeamMember = TeamMemberDTO.builder()
                .teamId(teamId)
                .userId(userId)
                .build();

        //when
        teamMemberRepository.addTeamMember(newTeamMember);

        //then
        assertEquals(3,newTeamMember.getTeamId());
    }


}
