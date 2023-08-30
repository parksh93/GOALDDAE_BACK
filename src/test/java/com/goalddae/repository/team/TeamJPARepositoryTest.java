package com.goalddae.repository.team;

import com.goalddae.entity.Team;
import com.goalddae.repository.TeamJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
public class TeamJPARepositoryTest {

    @Autowired
    TeamJPARepository teamJPARepository;

    @Test
    @Transactional
    @DisplayName("사이즈가 8")
    public void findAllTest(){
        //given

        //when
        List<Team> result = teamJPARepository.findAll();

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
        Team team = teamJPARepository.findTeamById(id);

        //then
        assertEquals("골때", team.getTeamName());
        assertEquals("서울", team.getArea());
    }

    @Test
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
        teamJPARepository.save(newTeam);
        List<Team> result = teamJPARepository.findAll();

        //then
        assertEquals(9, result.size());
        assertEquals("콜라", newTeam.getTeamName());
    }

    @Test
    @Transactional
    @DisplayName("id=17의 teamName을 골때->고올때 로, area를 서울->강원 으로 ")
    public void updateTest(){
        //given
        Long id = 17L;
        String teamName = "고올때";
        String area = "강원";

        Team team = teamJPARepository.findTeamById(id);

        team  = Team.builder()
                .id(team.getId())
                .teamName(teamName)
                .area(area)
                .averageAge(team.getAverageAge())
                .entryFee(team.getEntryFee())
                .entryGender(team.getEntryGender())
                .teamProfileImgUrl(null)
                .teamCreate(team.getTeamCreate())
                .teamProfileImgUrl(team.getTeamProfileImgUrl())
                .preferredDay(team.getPreferredDay())
                .preferredTime(team.getPreferredTime())
                .build();

        //when
         teamJPARepository.save(team);

         Team result = teamJPARepository.findTeamById(id);

        //then
        assertEquals(result.getArea(),area);
        assertEquals(result.getTeamName(),teamName);
    }

    @Test
    @Transactional
    @DisplayName("id=17을 삭제했을 경우, 전체 팀 사이즈 7")
    public void deleteTeamByIdTest(){
        // given
        Long id = 17L;

        // when
        teamJPARepository.deleteTeamById(id);

        // then
        assertEquals(7, teamJPARepository.findAll().size());
    }

    @Test
    @Transactional
    @DisplayName("teamName=골때 로 찾아왔을 시, Area=서울, 사이즈=1")
    public void findByTeamNameTest(){
        // given

        // when
        List<Team> result = teamJPARepository.findByTeamName("골때");

        // then
        assertEquals("서울", result.get(0).getArea());
        assertEquals(1, result.size());
    }

    @Test
    @Transactional
    @DisplayName("area=경기 로 검색 시, 사이즈=2")
    public void findByAreaTest(){
        //given

        //when
        List<Team> result = teamJPARepository.findByArea("경기");

        //then
        assertEquals(2, result.size());
    }

    @Test
    @Transactional
    @DisplayName("recruiting=false로 검색 시, 사이즈=3, 인덱스 0으로 검색 시 area=서울")
    public void findByRecruiting(){
        //given

        //when
        List<Team> result = teamJPARepository.findByRecruiting(false);

        //then
        assertEquals(3, result.size());
        assertEquals("서울", result.get(0).getArea());
    }

    @Test
    @Transactional
    @DisplayName("area=경기, recruiting=false로 검색하면 사이즈=1" +
            "area=null,recruiting=true 검색하면 사이즈=5" +
            "area=인천,recruiting=true 검색하면 사이즈=0")
    public void findByAreaAndRecruitingTest(){
        //given

        //when
        List<Team> result1 = teamJPARepository.findByAreaAndRecruiting("경기", false);
        List<Team> result2 = teamJPARepository.findByAreaAndRecruiting("인천",true);


        //then
        assertEquals(1, result1.size());
        assertEquals(0, result2.size());
    }

}
