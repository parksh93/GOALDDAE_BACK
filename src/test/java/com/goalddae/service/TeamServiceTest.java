package com.goalddae.service;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.dto.team.TeamUpdateDTO;
import com.goalddae.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Test
    @Transactional
    @DisplayName("사이즈가 8")
    public void findAllTest(){
        //given

        //when
        List<TeamListDTO> result = teamService.findAll();

        //then
        assertEquals(8, result.size());
    }

    @Test
    @Transactional
    @DisplayName("id=17로 가져왔을 때, teamName=골때, area=서울")
    public void findByIdTest(){
        //given
        Long id = 17L;

        //when
        Team team = teamService.findTeamById(id);

        //then
        assertEquals("골때", team.getTeamName());
        assertEquals("서울", team.getArea());
    }

    /*@Test
    @Transactional
    @DisplayName("teamName=콜라, 지역=인천,~~ 추가 후 팀명 확인 시 콜라")
    public void saveTest(){
        //given
        String teamName = "콜라";
        String area = "인천";
        int averageAge = 10;
        int entryFee = 10000;
        String entryGender = "남자";
        String teamProfileImgUrl = "./img/userProfileImg/goalddae_default_profile.Webp";
        String preferredDay ="월";
        String preferredTime = "오후2시";

        Team newTeam = Team.builder()
                .teamName(teamName)
                .area(area)
                .averageAge(averageAge)
                .entryFee(entryFee)
                .entryGender(entryGender)
                .teamProfileImgUrl(teamProfileImgUrl)
                .preferredDay(preferredDay)
                .preferredTime(preferredTime)
                .build();

        //when
        teamService.save(newTeam);
        List<TeamListDTO> result = teamService.findAll();

        //then
        assertEquals(9, result.size());
        assertEquals("콜라", newTeam.getTeamName());
    }
     */

    @Test
    @Transactional
    @DisplayName("id=17의 teamName을 골때->고올때 로, area를 서울->강원 으로 ")
    public void updateTest(){
        //given
        Long id = 17L;
        String teamName = "고올때";
        String area = "강원";

        Team team = teamService.findTeamById(id);

        TeamUpdateDTO teamUpdateDTO = TeamUpdateDTO.builder()
                .id(id)
                .teamName(teamName)
                .area(area)
                .entryGender(team.getEntryGender())
                .preferredDay(team.getPreferredDay())
                .preferredTime(team.getPreferredTime())
                .teamProfileImgUrl(team.getTeamProfileImgUrl())
              .build();

        //when
        teamService.update(teamUpdateDTO);

        Team result = teamService.findTeamById(id);

        //then
        assertEquals(result.getArea(),area);
        assertEquals(result.getTeamName(),teamName);
    }

    @Test
    @Transactional
    @DisplayName("id=17을 삭제하면 사이즈=7")
    public void deleteTeamByIdTest(){
        //given
        Long id = 17L;

        //when
        teamService.deleteTeamById(id);

        //then
        assertEquals(7, teamService.findAll().size());
        assertNull(teamService.findTeamById(id));
    }

    @Test
    @Transactional
    @DisplayName("teamName=골때 가져왔을 때 id=1, area=서울")
    public void findByTeamNameTest(){
        //given
        String searchTerm = "골때";

        // when
        List<Team> result = teamService.findByTeamName(searchTerm);

        // then
        assertEquals("서울", result.get(0).getArea());
        assertEquals(17, result.get(0).getId());
    }

    @Test
    @Transactional
    @DisplayName("area=경기 로 검색 시, 사이즈=2")
    public void findByAreaTest(){
        //given

        //when
        List<TeamListDTO> result = teamService.findByArea("경기");
        System.out.println(result);

        //then
        assertEquals(2, result.size());
        assertEquals("여자", result.get(0).getEntryGender());
    }

    @Test
    @Transactional
    @DisplayName("recruiting=false로 검색 시, 사이즈=3, 인덱스 0으로 검색 시 area=서울")
    public void findByRecruiting(){
        //given

        //when
        List<TeamListDTO> result = teamService.findByRecruiting(false);
        System.out.println(result);
        System.out.println(result.get(0));

        //then
        assertEquals(3, result.size());
        assertEquals("서울", result.get(0).getArea());
        assertEquals("여자", result.get(0).getEntryGender());
    }

    @Test
    @Transactional
    @DisplayName("area=경기, recruiting=false로 검색하면 사이즈=1" +
            "area=null,recruiting=true 검색하면 사이즈=5" +
            "area=인천,recruiting=true 검색하면 사이즈=0")
    public void findByAreaAndRecruitingTest(){
        //given

        //when
        List<TeamListDTO> result1 = teamService.findByAreaAndRecruiting("경기", false);
        List<TeamListDTO> result2 = teamService.findByAreaAndRecruiting("인천",true);


        //then
        assertEquals(1, result1.size());
        assertEquals("여자", result1.get(0).getEntryGender());
        assertEquals(0, result2.size());
    }
}

