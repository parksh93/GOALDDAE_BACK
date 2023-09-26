package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchDTO;
import com.goalddae.dto.match.TeamMatchInfoDTO;
import com.goalddae.dto.match.TeamMatchRequestDTO;
import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TeamMatchService {
    // 팀 매치 리스트 조회 - 일자, 지역, 남녀구분
    Page<TeamMatchDTO> getTeamMatches(Optional<Long> userId, LocalDate date, String province, String gender, int pageNumber, int pageSize);
    // 팀 매치 상세페이지 조회
    TeamMatchInfoDTO getTeamMatchDetail(Long teamMatchId);
    void applyForMatch(Long teamMatchId, TeamMatchRequestDTO request);
    void cancelTeamMatch(Long teamMatchId, Long userId, Long teamId);
}