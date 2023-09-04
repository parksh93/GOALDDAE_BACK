package com.goalddae.service;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.repository.IndividualMatchJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualMatchServiceImpl implements IndividualMatchService {

    private final IndividualMatchJPARepository individualMatchJPARepository;
    private final MatchStatusNotifier matchStatusNotifier;

    @Autowired
    public IndividualMatchServiceImpl(IndividualMatchJPARepository individualMatchJPARepository,
                                      MatchStatusNotifier matchStatusNotifier) {
        this.individualMatchJPARepository = individualMatchJPARepository;
        this.matchStatusNotifier = matchStatusNotifier;
    }

    // 매치 조회하여 유저에게 필요한 정보만 공개
    @Override
    public List<IndividualMatchDTO> getMatchesByDateAndProvince(LocalDate date, String province) {
        try {
            LocalDateTime startTime = date.atStartOfDay();
            LocalDateTime endTime = startTime.plusDays(1);

            List<IndividualMatch> matchesInProvince = individualMatchJPARepository.findByStartTimeBetweenAndReservationField_SoccerField_Province(startTime, endTime, province);

            return matchesInProvince.stream()
                    .map(match -> new IndividualMatchDTO(
                            match.getId(),
                            match.getReservationField().getId(),
                            match.getStartTime(),
                            match.getReservationField().getSoccerField().getFieldName(),
                            determineStatus(match),
                            match.getPlayerNumber(),
                            match.getGender()
                    ))
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("에러 메시지: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // 신청 가능 상태
    private String determineStatus(IndividualMatch match) {
        long currentRequestsCount = match.getRequests().size();
        long maxPlayerNumber = match.getPlayerNumber();
        LocalDateTime now = LocalDateTime.now();

        String status;

        if (now.isAfter(match.getStartTime())) {
            status = "마감";
            // 마감임박 기준은 경기 시작이 2시간 미만으로 남았을 경우
        } else if (now.plusHours(2).isAfter(match.getStartTime())) {
            status = "마감임박";
        } else if (currentRequestsCount == maxPlayerNumber) {
            status = "마감";
            // 마감임박 기준은 신청 인원이 최대 인원의 80% 이상일 때
        } else if (currentRequestsCount >= maxPlayerNumber * 0.8 ) {
            status = "마감임박";
        } else {
            status = "신청가능";
        }

        // 웹소켓을 통해 클라이언트에게 매치 상태 변경 알림 전송
        this.matchStatusNotifier.notifyMatchStatusChange(match.getId(), status);

        return status;
    }
}

