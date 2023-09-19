package com.goalddae.service;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.dto.match.IndividualMatchRequestDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.repository.IndividualMatchJPARepository;
import com.goalddae.repository.IndividualMatchRequestJPARepository;
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
    private final IndividualMatchRequestJPARepository individualMatchRequestJPARepository;
    private final MatchStatusNotifier matchStatusNotifier;

    @Autowired
    public IndividualMatchServiceImpl(IndividualMatchJPARepository individualMatchJPARepository,
                                      IndividualMatchRequestJPARepository individualMatchRequestJPARepository,
                                      MatchStatusNotifier matchStatusNotifier) {
        this.individualMatchJPARepository = individualMatchJPARepository;
        this.individualMatchRequestJPARepository = individualMatchRequestJPARepository;
        this.matchStatusNotifier = matchStatusNotifier;
    }

    // 타임라인 - 일자, 지역, 레벨, 남녀구분
    @Override
    public List<IndividualMatchDTO> getMatchesByDateAndProvinceAndLevelAndGender(
            LocalDate date, String province, String level, String gender) {
        try {
            LocalDateTime startTime = date.atStartOfDay();
            LocalDateTime endTime = startTime.plusDays(1);

            if ("남녀모두".equals(gender) || gender == null || "".equals(gender)) {
                gender = null;
            }

            List<IndividualMatch> matchesInProvince = individualMatchJPARepository
                    .findMatches(
                            startTime, endTime, province, level, gender);

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
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<IndividualMatchRequest> findAllByUserId(long userId) {
        List<IndividualMatchRequest> individualMatchRequestList
                = individualMatchRequestJPARepository.findAllByUserId(userId);
        return individualMatchRequestList;
    }

    // 신청 가능 상태
    private String determineStatus(IndividualMatch match) {
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

