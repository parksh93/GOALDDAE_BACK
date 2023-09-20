package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;
import com.goalddae.entity.User;
import com.goalddae.repository.TeamMatchJPARepository;
import com.goalddae.repository.TeamMatchRequestJPARepository;
import com.goalddae.repository.UserJPARepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamMatchServiceImpl implements TeamMatchService {

    private final TeamMatchJPARepository teamMatchJPARepository;

    private final TeamMatchRequestJPARepository teamMatchRequestJPARepository;

    private final UserJPARepository userJPARepository;

    private final MatchStatusNotifier matchStatusNotifier;

    @Autowired
    public TeamMatchServiceImpl(TeamMatchJPARepository teamMatchJPARepository,
                                TeamMatchRequestJPARepository teamMatchRequestJPARepository,
                                UserJPARepository userJPARepository,
                                MatchStatusNotifier matchStatusNotifier) {

        this.teamMatchJPARepository = teamMatchJPARepository;
        this.teamMatchRequestJPARepository = teamMatchRequestJPARepository;
        this.userJPARepository = userJPARepository;
        this.matchStatusNotifier = matchStatusNotifier;
    }

    // 홈팀 - 팀장이 팀 매치 예약
    @Override
    public void createTeamMatch(TeamMatch teammatch) {
        teamMatchJPARepository.save(teammatch);
    }

    // 어웨이팀 - 타 팀이 팀 매치 신청
    @Override
    public void requestTeamMatch(TeamMatchRequest request) {
        teamMatchRequestJPARepository.save(request);
    }

    // 팀 매치 리스트 조회 - 일자, 지역, 남녀구분
    @Override
    public Page<TeamMatchDTO> getTeamMatches(Optional<Long> userId, LocalDate date,
                                             String province, String gender,
                                             int page, int size) {
        try {
            LocalDateTime startTime = date.atStartOfDay();
            LocalDateTime endTime = startTime.plusDays(1);

            if ("남녀모두".equals(gender) || gender == null || "".equals(gender)) {
                gender = null;
            }

            String defaultCity = "서울";

            if (userId != null && userId.isPresent()) {
                User user = userJPARepository.findById(userId.get())
                        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId.get()));
                if (user.getPreferredCity() != null && !user.getPreferredCity().isEmpty()) {
                    defaultCity = user.getPreferredCity();
                }
            }

            if (province == null || province.isEmpty()) {
                province = defaultCity;
            }

            Pageable pageable = PageRequest.of(page, size);

            Page<TeamMatch> matchesInProvince = teamMatchJPARepository.findMatches(
                    startTime, endTime, province, gender, pageable);

            return new PageImpl<>(
                    matchesInProvince.stream()
                            .map(match -> new TeamMatchDTO(
                                    match.getId(),
                                    match.getReservationField().getId(),
                                    match.getStartTime(),
                                    match.getReservationField().getSoccerField().getFieldName(),
                                    determineStatus(match),
                                    (int)match.getPlayerNumber(),
                                    match.getGender()))
                            .collect(Collectors.toList()), pageable , matchesInProvince.getTotalElements());
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();

            return new PageImpl<>(new ArrayList<>());
        }
    }

    // 신청 가능 상태
    private String determineStatus(TeamMatch match) {
        long currentRequestsCount = match.getRequests().size();
        long maxPlayerNumber = match.getPlayerNumber();
        LocalDateTime now = LocalDateTime.now();

        String status;

        if (currentRequestsCount == maxPlayerNumber) {
            status = "마감";
        } else if (currentRequestsCount >= maxPlayerNumber * 0.8 ) {
            status = "마감임박";
        } else if (now.isAfter(match.getStartTime())) {
            status = "마감";
        } else if (now.plusHours(2).isAfter(match.getStartTime())) {
            status = "마감임박";
        } else {
            status = "신청가능";
        }

        // 웹소켓을 통해 클라이언트에게 매치 상태 변경 알림 전송
        this.matchStatusNotifier.notifyMatchStatusChange(match.getId(), status);

        return status;
    }
}