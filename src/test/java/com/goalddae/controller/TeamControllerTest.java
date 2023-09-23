package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.goalddae.dto.team.TeamApplyDTO;
import com.goalddae.dto.team.TeamUpdateDTO;
import com.goalddae.entity.Team;
import com.goalddae.repository.TeamJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired  //  데이터 직렬화에 사용하는 객체
    private ObjectMapper objectMapper;

    @Autowired    // 서비스 레이어의 메서드는 추후에 쿼리문 두 개 이상 호출할 수도 있고, 그런 변경이 생겼을 때 테스트코드도 같이 수정할 가능성이 생김
    private TeamJPARepository teamJPARepository;

    @BeforeEach

    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    @DisplayName("전부 가져왔을 때, 사이즈=8, 0번째 요소의 id=17")
    void findAllTest() throws Exception {
        //given
        String url = "/team/list";
        Long id = 17L;

        //when: 해당 주소로 접속 후 json데이터 리턴받아 저장,
        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        //then: 리턴받은 json목록의 0번째 요소의 id가 17인지 확인, 사이즈가 8인지 확인
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(8))
                .andExpect(jsonPath("$[0].id").value(id));
    }

    @Test
    @Transactional
    @DisplayName("id=17로 검색 시 teamName=골때")
    public void teamDetailTest() throws Exception {
        //given
        Long id = 17L;
        String teamName = "골때";
        String area = "서울";
        String url = "/team/detail/17";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName").value(teamName))
                .andExpect(jsonPath("$.area").value(area));
    }

    @Test
    @Transactional
    @DisplayName("id=17 삭제하면 전체 사이즈=7, area확인 시 null")
    public void teamDeleteTest() throws Exception {
        //given
        Long id = 17L;
        String url = "/team/delete/{id}";

        //when
        ResultActions result = mockMvc.perform(delete(url, id)
                .accept(MediaType.TEXT_PLAIN));

        //then
        List<Team> teamList = teamJPARepository.findAll();
        assertEquals(7, teamList.size());

        Team team = teamJPARepository.findTeamById(id);
        assertNull(team);
    }

    @Test
    @Transactional
    @DisplayName("Team 추가 후 확인 시 전체사이즈 9, area=제주")
    public void teamSaveTest() throws Exception {
        //given
        Long id = 16L;
        String teamName = "콜라";
        String area = "인천";
        int averageAge = 10;
        int entryFee = 10000;

        boolean recruiting = true;
        String teamIntroduce = "안녕하세요";
        String entryGender = "남자";
        String teamProfileImgUrl = "./img/userProfileImg/goalddae_default_profile.Webp";
        String preferredDay = "월";
        String preferredTime = "오후2시";

        String url = "/team/teamSave";
        String url2 = "/team/list";

        Team team = new Team(id, teamName, area, averageAge, recruiting, teamIntroduce, entryFee, entryGender, teamProfileImgUrl, preferredDay, preferredTime);

        // 데이터 직렬화(java-> json)
        final String requestBody = objectMapper.writeValueAsString(team);

        //when
        mockMvc.perform(post(url) //    url에 요청(post)
                .contentType(MediaType.APPLICATION_JSON)    // 위에서 직렬화한 requestBody 변수 전달
                .content(requestBody));

        //then: 전체데이터
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(9))
                .andExpect(jsonPath("$[8].area").value(area));
    }

    @Test
    @Transactional
    @DisplayName("teamName,area 수정")
    public void teamUpdateTest() throws Exception {
        Long id = 17L;
        String teamName = "수정된 팀이름";
        String area = "수정된 지역";
        int averageAge = 20;
        int entryFee = 20000;
        String teamIntroduce = null;
        boolean recruiting = false;
        String entryGender = "여자";
        String teamProfileImgUrl = "./img/userProfileImg/goalddae_default_profile.Webp";
        String preferredTime = "오후1시";
        String preferredDay = "토요일";

        String url = "/team/update";
        String url2 = "/team/detail/" + id;

        TeamUpdateDTO teamUpdateDTO = TeamUpdateDTO.builder()
                .id(id)
                .teamName(teamName)
                .area(area)
                .averageAge(averageAge)
                .recruiting(recruiting)
                .entryFee(entryFee)
                .entryGender(entryGender)
                .teamIntroduce(teamIntroduce)
                .teamProfileImgUrl(teamProfileImgUrl)
                .preferredDay(preferredDay)
                .preferredTime(preferredTime)
                .build();

        // 데이터 직렬화
        final String requestBody = objectMapper.writeValueAsString(teamUpdateDTO);

        // when
        mockMvc.perform(patch(url)  // url에 patch로
                .contentType(MediaType.APPLICATION_JSON)    // 보내는 데이터 JSON
                .content(requestBody)); // 직렬화된 requestBody 전송

        final String requestBody2 = objectMapper.writeValueAsString(id);
        // then
        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName").value(teamName))
                .andExpect(jsonPath("$.area").value(area));

    }


    @Test
    @Transactional
    @DisplayName("teamName=골때 찾으면 id=17, area=서울")
    public void searchTeamNameTest() throws Exception {
        //given
        Long id = 17L;
        String searchTerm = "골때";
        String teamName = "골때";
        String area = "서울";
        String url = "/team/search/teamName";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .param("searchTerm", teamName)
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].area").value(area));
    }

    @Test
    @Transactional
    @DisplayName("area=경기로 검색 시 1번째 요소 이름 골때4, 전체 사이즈=2")
    public void filterAreaTest() throws Exception {
        //given
        String area = "경기";
        String teamName = "골때4";
        String url = "/team/list/area";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .param("area", "경기")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teamName").value(teamName))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Transactional
    @DisplayName("recruiting=true로 검색하면 전체사이즈=5, 2번째 요소[1]의 teamName=골때3 ")
    public void filterRecruitingTest() throws Exception{
        //given
        boolean recruiting = true;
        String teamName = "골때3";
        String url = "/team/list/recruiting";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .param("recruiting", "true")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].teamName").value(teamName))
                .andExpect(jsonPath("$.length()").value(5));

    }

    @Test
    @Transactional
    @DisplayName("area=경기,recruiting=true로 검색 시 사이즈=1")
    public void filterAreaAndRecruitingTest() throws Exception{
        //given
        String area = "경기";
        boolean recruiting = true;
        String url = "/team/list/areaAndRecruiting";

        //when
        final ResultActions result = mockMvc.perform(get(url)
                .param("area", "경기")
                .param("recruiting", "true")
                .accept(MediaType.APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Transactional
    @DisplayName("")
    public void rejectApplyTest() throws Exception{
        //given
        long userId = 2;
        long teamId = 5;
        int teamAcceptStatus = 2;
        String url = "/team/rejectApply";

        TeamApplyDTO apply = TeamApplyDTO.builder()
                .userId(userId)
                .teamId(teamId)
                .teamAcceptStatus(2)
                .build();

        final String requestBody = objectMapper.writeValueAsString(apply);

        //when
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
    }
}