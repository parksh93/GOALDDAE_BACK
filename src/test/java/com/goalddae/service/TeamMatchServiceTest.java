package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchDTO;
import com.goalddae.repository.TeamMatchJPARepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
public class TeamMatchServiceTest {

    @Autowired
    private TeamMatchService teamMatchService;

    @Autowired
    private TeamMatchJPARepository teammatchJPARepository;

    @Test
    @DisplayName("팀매치 조회 테스트 - 일자, 지역, 남녀구분")
    public void findTeamMatchesByConditions() {
        // Given
        LocalDate date = LocalDate.now();
        String province = "서울";
        String gender = "남녀모두";

        // When
        Page<TeamMatchDTO> resultPage = teamMatchService.getTeamMatches(
                Optional.empty(),
                date,
                province,
                gender,
                0, 10);

        // Then
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).isNotEmpty();

        for (TeamMatchDTO returnedDto : resultPage.getContent()) {
            assertThat(returnedDto.getStartTime().toLocalDate()).isEqualTo(date);
            assertThat(returnedDto.getFieldName()).isEqualTo(province);
            assertThat(returnedDto.getGender()).isEqualTo(gender);
        }
    }
}
