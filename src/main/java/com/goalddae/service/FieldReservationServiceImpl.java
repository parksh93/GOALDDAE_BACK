package com.goalddae.service;

import com.goalddae.dto.fieldReservation.FieldReservationDTO;
import com.goalddae.entity.*;
import com.goalddae.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldReservationServiceImpl implements FieldReservationService {

    ReservationFieldJPARepository reservationFieldJPARepository;
    IndividualMatchJPARepository individualMatchJPARepository;
    TeamMatchJPARepository teamMatchJPARepository;
    SoccerFieldRepository soccerFieldRepository;
    UserJPARepository userJPARepository;

    @Autowired
    public FieldReservationServiceImpl(ReservationFieldJPARepository reservationFieldJPARepository, IndividualMatchJPARepository individualMatchJPARepository, TeamMatchJPARepository teamMatchJPARepository,
                                       SoccerFieldRepository soccerFieldRepository, UserJPARepository userJPARepository) {
        this.reservationFieldJPARepository = reservationFieldJPARepository;
        this.individualMatchJPARepository = individualMatchJPARepository;
        this.teamMatchJPARepository = teamMatchJPARepository;
        this.soccerFieldRepository = soccerFieldRepository;
        this.userJPARepository = userJPARepository;
    }

    @Override
    @Transactional
    public void CreateReservationFieldAndMatch(FieldReservationDTO dto) {

        LocalDateTime reservedDate = LocalDateTime.of(
                Integer.parseInt(dto.getReservedDate().substring(0, 4)), // 연도
                Integer.parseInt(dto.getReservedDate().substring(4, 6)), // 월
                Integer.parseInt(dto.getReservedDate().substring(6, 8)), // 일
                0,
                0
        );

        LocalDateTime startDate = reservedDate.plusHours(dto.getStartTime());

        LocalDateTime endDate = startDate.plusHours(dto.getTotalHours());

        Long soccerFieldId = dto.getSoccerFieldId();
        Long userId = dto.getUserId();
        Long playerNumber = dto.getPlayerNumber();
        String gender = dto.getGender();
        String level = dto.getLevel();
        Long teamId = dto.getTeamId();

        SoccerField soccerField = soccerFieldRepository.findById(soccerFieldId)
                .orElseThrow(() -> new EntityNotFoundException("SoccerField not found with id: " + soccerFieldId));

        User user = userJPARepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("userld not found with id: " + userId));

        // 구장 예약 정보 생성 및 저장
        ReservationField reservationField = ReservationField.builder()
                .soccerField(soccerField) // 예약할 구장
                .reservedDate(reservedDate)
                .startDate(startDate)
                .endDate(endDate)
                .user(user) // 예약한 유저
                .build();
        reservationFieldJPARepository.save(reservationField);

        if(teamId != -1L){
            // 팀매치 정보 생성 및 저장
            TeamMatch teamMatch = TeamMatch.builder()
                    .startTime(startDate) // 매치 시작 시간
                    .endTime(endDate) // 매치 종료 시간
                    .playerNumber(playerNumber) // 매치 유저 수
                    .gender(gender) // 남녀 구분
                    .level(level) // 레벨
                    .homeUser(user) // 매치할 유저 (홈팀 유저)
                    .homeTeamId(teamId) // 매치할 팀 (홈팀 id)
                    .reservationField(reservationField) // 예약한 구장
                    .build();
            teamMatchJPARepository.save(teamMatch);
        } else {
            // 개인매치 정보 생성 및 저장
            IndividualMatch individualMatch = IndividualMatch.builder()
                    .startTime(startDate) // 매치 시작 시간
                    .endTime(endDate) // 매치 종료 시간
                    .playerNumber(playerNumber) // 매치 유저 수
                    .gender(gender) // 남녀 구분
                    .level(level) // 레벨
                    .user(user) // 매치할 유저
                    .reservationField(reservationField) // 예약한 구장
                    .build();
            individualMatchJPARepository.save(individualMatch);
        }
    }

    @Transactional
    public List<Integer> getReservationTimesByFieldIdAndDate(long fieldId, String date) {
        // 문자열 형식의 date를 LocalDateTime으로 변환
        LocalDateTime localDateTime = LocalDateTime.of(
                Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(4, 6)),
                Integer.parseInt(date.substring(6, 8)),
                0, 0
        );

        List<ReservationField> reservationFields = reservationFieldJPARepository
                .findBySoccerFieldIdAndReservedDate(fieldId, localDateTime);

        return reservationFields.stream()
                .map(reservationField -> reservationField.getStartDate().toLocalTime().getHour())
                .collect(Collectors.toList());
    }
}