package com.goalddae.service;

import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.repository.IndividualMatchJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualMatchServiceImpl implements IndividualMatchService {

    private final IndividualMatchJPARepository individualMatchJPARepository;

    @Autowired
    public IndividualMatchServiceImpl(IndividualMatchJPARepository individualMatchJPARepository) {
        this.individualMatchJPARepository = individualMatchJPARepository;
    }

    // 매치 조회하여 유저에게 필요한 정보만 공개
    @Override
    public List<IndividualMatchDTO> getMatchesByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        List<IndividualMatch> matches = individualMatchJPARepository.findByStartTimeBetweenOrderByStartTimeAsc(startOfDay, endOfDay);

        return matches.stream()
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
    }

    // 신청 가능 상태
    private String determineStatus(IndividualMatch match) {
        long currentRequestsCount = match.getRequests().size();
        long maxPlayerNumber = match.getPlayerNumber();
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(match.getStartTime())) {
            return "마감";
            // 마감임박 기준은 경기 시작이 1시간 미만으로 남았을 경우
        } else if (now.plusHours(1).isAfter(match.getStartTime())) {
            return "마감임박";
        } else if (currentRequestsCount == maxPlayerNumber) {
            return "마감";
            // 마감임박 기준은 신청 인원이 최대 인원의 80% 이상일 때
        } else if (currentRequestsCount >= maxPlayerNumber * 0.8 ) {
            return "마감임박";
        } else {
            return "신청가능";
        }
    }
}
