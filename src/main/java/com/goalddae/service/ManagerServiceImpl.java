package com.goalddae.service;

import com.goalddae.dto.manager.ManagerIndividualMatchDTO;
import com.goalddae.dto.manager.ManagerUserInfoDTO;
import com.goalddae.entity.IndividualMatch;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.ReservationField;
import com.goalddae.entity.User;
import com.goalddae.repository.IndividualMatchJPARepository;
import com.goalddae.repository.UserJPARepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService{

    private final IndividualMatchJPARepository individualMatchJPARepository;

    private final UserJPARepository userJPARepository;

    @Autowired
    public ManagerServiceImpl(IndividualMatchJPARepository individualMatchJPARepository, UserJPARepository userJPARepository){
        this.individualMatchJPARepository = individualMatchJPARepository;
        this.userJPARepository = userJPARepository;
    }

    @Override
    public List<ManagerIndividualMatchDTO> getFinishedMatches(Long managerId) {

        LocalDateTime currentTime = LocalDateTime.now();
        // IndividualMatch 엔티티를 가져옴
        List<IndividualMatch> matches = individualMatchJPARepository.findByManagerIdAndStartTimeBeforeOrderByStartTimeDesc(managerId, currentTime);

        // ManagerIndividualMatchDTO 리스트를 생성하여 필요한 정보 매핑
        List<ManagerIndividualMatchDTO> matchDTOs = new ArrayList<>();
        for (IndividualMatch match : matches) {
            ManagerIndividualMatchDTO dto = ManagerIndividualMatchDTO.builder()
                    .id(match.getId())
                    .startTime(match.getStartTime())
                    .endTime(match.getEndTime())
                    .playerNumber(match.getPlayerNumber())
                    .gender(match.getGender())
                    .level(match.getLevel())
                    .userId(match.getUser().getId()) // User 엔티티에서 id를 가져옴
                    .build();

            // ReservationField에서 필요한 정보 가져오기
            ReservationField reservationField = match.getReservationField();
            if (reservationField != null) {
                dto.setFieldId(reservationField.getSoccerField().getId());
                dto.setFieldName(reservationField.getSoccerField().getFieldName());
            }

            matchDTOs.add(dto);
        }

        return matchDTOs;
    }

    public List<ManagerUserInfoDTO> getMatchParticipants(Long matchId) {
        IndividualMatch match = individualMatchJPARepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Match not found"));

        List<ManagerUserInfoDTO> participants = new ArrayList<>();

        // 첫 번째 유저 (매치 생성자)
        ManagerUserInfoDTO firstUserDTO = getUserDTO(match.getUser());
        participants.add(firstUserDTO);

        // 나머지 유저 (참가 신청자)
        for (IndividualMatchRequest request : match.getRequests()) {
            ManagerUserInfoDTO userDTO = getUserDTO(request.getUser());
            participants.add(userDTO);
        }

        return participants;
    }

    @Transactional
    @Override
    public void increaseNoShowCount(List<Long> userIds) {
        for (Long userId : userIds) {
            User user = userJPARepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

            // 노쇼 횟수 증가
            user.noShowCntUp();
        }
    }

    private ManagerUserInfoDTO getUserDTO(User user) {
        ManagerUserInfoDTO dto = ManagerUserInfoDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .birth(user.getBirth())
                .matchesCnt(user.getMatchesCnt())
                .level(user.getLevel())
                .signupDate(user.getSignupDate())
                .noShowCnt(user.getNoShowCnt())
                .build();
        return dto;
    }



}
