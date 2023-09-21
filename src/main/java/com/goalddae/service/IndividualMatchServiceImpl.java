package com.goalddae.service;


import com.goalddae.dto.match.IndividualMatchDTO;
import com.goalddae.dto.match.IndividualMatchRequestDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.repository.IndividualMatchJPARepository;
import com.goalddae.repository.IndividualMatchRequestJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.goalddae.dto.match.*;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.User;
import com.goalddae.exception.ErrorCode;
import com.goalddae.exception.NotMatchConditionsException;
import com.goalddae.exception.OverPlayerNumMatchException;
import com.goalddae.repository.IndividualMatchJPARepository;
import com.goalddae.repository.IndividualMatchRequestJPARepository;
import com.goalddae.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualMatchServiceImpl implements IndividualMatchService {

    private final IndividualMatchJPARepository individualMatchJPARepository;
    private final IndividualMatchRequestJPARepository individualMatchRequestJPARepository;
    private final MatchStatusNotifier matchStatusNotifier;
    private final UserJPARepository userJPARepository;

    @Autowired
    public IndividualMatchServiceImpl(IndividualMatchJPARepository individualMatchJPARepository,
                                      IndividualMatchRequestJPARepository individualMatchRequestJPARepository,
                                      MatchStatusNotifier matchStatusNotifier,
                                      UserJPARepository userJPARepository) {
        this.individualMatchJPARepository = individualMatchJPARepository;
        this.individualMatchRequestJPARepository = individualMatchRequestJPARepository;
        this.matchStatusNotifier = matchStatusNotifier;
        this.userJPARepository = userJPARepository;
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

            if ("레벨".equals(level) || level == null || "".equals(level)) {
                level = null;
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
    // 신청 가능 상태
    private String determineStatus(IndividualMatch match) {
        long currentRequestsCount = match.getRequests().size();
        long maxPlayerNumber = match.getPlayerNumber()-1;
        LocalDateTime now = LocalDateTime.now();

        String status;

        if (now.isAfter(match.getStartTime())) {
            status = "종료";
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


    @Override
    public List<Object> findAllByUserId(long userId) {
        List<IndividualMatchRequest> individualMatchRequestList
                = individualMatchRequestJPARepository.findAllByUserId(userId);

        List<IndividualMatch> individualMatchList
                = individualMatchJPARepository.findAllByUserId(userId);

        List<Object> combinedList = new ArrayList<>();

        // 사용자가 신청한 매치를 먼저 추가
        combinedList.addAll(individualMatchRequestList);

        // 사용자가 생성한 매치 중에서 userId와 일치하는 것이 있으면 추가
        for (IndividualMatch individualMatch : individualMatchList) {
            if (individualMatch.getUser().getId() == userId) {
                // IndividualMatch 객체를 추가
                combinedList.add(individualMatch);
            }
        }

        return combinedList;
    }


    @Override
    public IndividualMatchDetailDTO findById(long matchId) {
        IndividualMatch individualMatch = individualMatchJPARepository.findById(matchId).get();

        IndividualMatchDetailDTO individualMatchDetailDTO = IndividualMatchDetailDTO.builder()
                .id(matchId)
                .startTime(LocalTime.from(individualMatch.getStartTime()))
                .endTime(LocalTime.from(individualMatch.getEndTime()))
                .status(determineStatus(individualMatch))
                .gender(individualMatch.getGender())
                .limitLevel(individualMatch.getLevel())
                .playerNumber(individualMatch.getPlayerNumber())
                .fieldId(individualMatch.getReservationField().getSoccerField().getId())
                .fieldImg1(individualMatch.getReservationField().getSoccerField().getFieldImg1())
                .fieldImg2(individualMatch.getReservationField().getSoccerField().getFieldImg2())
                .fieldImg3(individualMatch.getReservationField().getSoccerField().getFieldImg3())
                .province(individualMatch.getReservationField().getSoccerField().getProvince())
                .region(individualMatch.getReservationField().getSoccerField().getRegion())
                .address(individualMatch.getReservationField().getSoccerField().getAddress())
                .fieldName(individualMatch.getReservationField().getSoccerField().getFieldName())
                .fieldSize(individualMatch.getReservationField().getSoccerField().getFieldSize())
                .grassWhether(individualMatch.getReservationField().getSoccerField().getGrassWhether())
                .inOutWhether(individualMatch.getReservationField().getSoccerField().getInOutWhether())
                .parkingStatus(individualMatch.getReservationField().getSoccerField().isParkingStatus())
                .showerStatus(individualMatch.getReservationField().getSoccerField().isShowerStatus())
                .toiletStatus(individualMatch.getReservationField().getSoccerField().isToiletStatus())
                .content(individualMatch.getReservationField().getSoccerField().getContent())
                .userId(individualMatch.getUser().getId())
                .nickname(individualMatch.getUser().getNickname())
                .profileImgUrl(individualMatch.getUser().getProfileImgUrl())
                .level(individualMatch.getUser().getLevel())
                .playDate(individualMatch.getStartTime().toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();

        return individualMatchDetailDTO;
    }

    @Override
    @Transactional
    public void saveMatchRequest(SaveIndividualMatchDTO saveIndividualMatchDTO) {
        IndividualMatch individualMatch = individualMatchJPARepository.findById(saveIndividualMatchDTO.getMatchId()).get();
        int playerCnt = individualMatchRequestJPARepository.countByIndividualMatchId(saveIndividualMatchDTO.getMatchId());

        if(playerCnt < (individualMatch.getPlayerNumber() - 1)){
            User user = userJPARepository.findById(saveIndividualMatchDTO.getUserId()).get();
            if(individualMatch.getGender().equals("남녀모두") && user.getLevel().equals(individualMatch.getLevel())){
                saveIndividualMatchRequest(individualMatch, user);
            }else {
                if(user.getGender().equals(individualMatch.getGender()) && user.getLevel().equals(individualMatch.getLevel())){
                    saveIndividualMatchRequest(individualMatch, user);
                }else {
                    throw new NotMatchConditionsException(ErrorCode.NOT_MATCH_CONDITIONS);
                }
            }
        }else {
            throw  new OverPlayerNumMatchException(ErrorCode.OVER_PLAYER_NUM_MATCH);
        }
    }

    private void saveIndividualMatchRequest(IndividualMatch individualMatch, User user){
        IndividualMatchRequest individualMatchRequest = IndividualMatchRequest
                .builder().
                individualMatch(individualMatch)
                .user(user)
                .build();
        individualMatchRequestJPARepository.save(individualMatchRequest);
    }

    @Override
    public List<GetPlayerInfoDTO> getMatchPlayerInfo(long matchId) {
        return individualMatchRequestJPARepository.findIndividualMatchPlayerList(matchId);
    }

    @Override
    public void cancelMatchRequest(CancelMatchRequestDTO cancelMatchRequestDTO) {
        individualMatchRequestJPARepository.deleteByUserIdAndIndividualMatchId(cancelMatchRequestDTO.getUserId(), cancelMatchRequestDTO.getMatchId());
    }
}