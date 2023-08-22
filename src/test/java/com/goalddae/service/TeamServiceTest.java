package com.goalddae.service;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Test
    @Transactional
    @DisplayName("teamName=골때 가져왔을 때 id=1, area=서울")
    public void findByTeamNameTest(){
        //given
        String searchTerm = "골때";

        // when
        List<Team> result = teamService.findByTeamName(searchTerm);

        // then
        for (Team team : result) {
            assertEquals("서울", team.getArea());
            assertEquals(1, team.getId());
        }

    }


}
