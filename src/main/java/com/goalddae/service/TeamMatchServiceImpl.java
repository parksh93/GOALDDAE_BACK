package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchDTO;
import com.goalddae.dto.match.TeamMatchRequestDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;
import com.goalddae.entity.User;
import com.goalddae.dto.match.TeamMatchInfoDTO;
import com.goalddae.entity.*;
import com.goalddae.repository.TeamJPARepository;
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
    private final TeamJPARepository teamJPARepository;

    @Autowired
    public TeamMatchServiceImpl(TeamMatchJPARepository teamMatchJPARepository,
                                TeamMatchRequestJPARepository teamMatchRequestJPARepository,
                                UserJPARepository userJPARepository,
                                MatchStatusNotifier matchStatusNotifier,
                                TeamJPARepository teamJPARepository) {

        this.teamMatchJPARepository = teamMatchJPARepository;
        this.teamMatchRequestJPARepository = teamMatchRequestJPARepository;
        this.userJPARepository = userJPARepository;
        this.matchStatusNotifier = matchStatusNotifier;
        this.teamJPARepository =teamJPARepository;
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

    public TeamMatchInfoDTO convertToDto(TeamMatch teamMatch) {
        TeamMatchInfoDTO dto = new TeamMatchInfoDTO();
        dto.setId(teamMatch.getId());
        dto.setFieldId(teamMatch.getReservationField().getId());
        dto.setStartTime(teamMatch.getStartTime());
        dto.setEndTime(teamMatch.getEndTime());
        dto.setFieldName(teamMatch.getReservationField().getSoccerField().getFieldName());
        dto.setStatus(determineStatus(teamMatch));
        dto.setPlayerNumber((int)teamMatch.getPlayerNumber());
        dto.setGender(teamMatch.getGender());
        dto.setToiletStatus(teamMatch.getReservationField().getSoccerField().isToiletStatus());
        dto.setShowerStatus(teamMatch.getReservationField().getSoccerField().isShowerStatus());
        dto.setParkingStatus(teamMatch.getReservationField().getSoccerField().isParkingStatus());
        dto.setFieldSize(teamMatch.getReservationField().getSoccerField().getFieldSize());
        dto.setGrassWhether(teamMatch.getReservationField().getSoccerField().getGrassWhether());
        dto.setInOutWhether(teamMatch.getReservationField().getSoccerField().getInOutWhether());
        dto.setProvince(teamMatch.getReservationField().getSoccerField().getProvince());
        dto.setRegion(teamMatch.getReservationField().getSoccerField().getRegion());
        dto.setAddress(teamMatch.getReservationField().getSoccerField().getAddress());
        dto.setFieldImg1(teamMatch.getReservationField().getSoccerField().getFieldImg1());
        dto.setFieldImg2(teamMatch.getReservationField().getSoccerField().getFieldImg2());
        dto.setFieldImg3(teamMatch.getReservationField().getSoccerField().getFieldImg3());

        return dto;
    }

    @Override
    public TeamMatchInfoDTO getTeamMatchDetail(Long teamMatchId) {
        TeamMatch teamMatch = teamMatchJPARepository.findWithSoccerFieldById(teamMatchId)
                .orElseThrow(() -> new IllegalArgumentException("No match found with ID " + teamMatchId));

        return convertToDto(teamMatch);
    }

    @Override
    @Transactional
    public void applyForMatch(Long teamMatchId, TeamMatchRequestDTO request) {
        // 1. 먼저 TeamMatch 객체를 데이터베이스에서 찾습니다.
        TeamMatch teamMatch = teamMatchJPARepository.findById(teamMatchId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match Id:" +  teamMatchId));

        // 2. 신청한 유저와 그의 팀이 유효한지 확인합니다.
        User awayUser = userJPARepository.findById(request.getAwayUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + request.getAwayUserId()));

        // 3. 어웨이팀 정보를 업데이트합니다.
        teamMatch.applyAway(awayUser, request.getAwayTeamId());

        // 4. 변경된 정보를 데이터베이스에 저장합니다.
        teamMatchJPARepository.save(teamMatch);
    }
}
