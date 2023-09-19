package com.goalddae.service;

import com.goalddae.entity.TeamMatch;
import com.goalddae.repository.TeamMatchJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TeamMatchServiceTest {

    @Autowired
    private TeamMatchService teamMatchService;

    @MockBean
    private TeamMatchJPARepository teamMatchJPARepository;

    @Test
    @DisplayName("팀매치 조회 테스트 - 일자, 지역, 남녀구분")
    public void findTeamMatchesByConditions() {
        // Given
        LocalDate date = LocalDate.now();
        String province = "서울";
        String gender = "남성";
        int page = 0;
        int size = 10;

        LocalDateTime startTime = date.atStartOfDay();
        LocalDateTime endTime = startTime.plusDays(1);

        Pageable pageable = PageRequest.of(page, size);

        // 생성할 mock 객체
        TeamMatch teamMatchMock1 = mock(TeamMatch.class);
        TeamMatch teamMatchMock2 = mock(TeamMatch.class);

        List<TeamMatch> matchesInProvinceList= new ArrayList<>();
        matchesInProvinceList.add(teamMatchMock1);
        matchesInProvinceList.add(teamMatchMock2);

        // when-thenReturn 구문에서 사용될 Page 객체 생성
        Page<TeamMatch> matchesInProvincePage= new PageImpl<>(matchesInProvinceList, pageable , matchesInProvinceList.size());

        when(teamMatchJPARepository.findMatches(
                startTime, endTime, province, gender, pageable)).thenReturn(matchesInProvincePage);

        // When & Then
        assertNotNull(teamMatchService.getTeamMatches(Optional.empty(), date,
                province,
                gender,
                page,
                size));
    }
}
