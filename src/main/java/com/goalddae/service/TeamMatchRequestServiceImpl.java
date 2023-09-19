package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchRequestDTO;
import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;
import com.goalddae.entity.User;
import com.goalddae.repository.TeamMatchJPARepository;
import com.goalddae.repository.TeamMemberRepository;
import com.goalddae.repository.TeamMatchRequestJPARepository;
import com.goalddae.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamMatchRequestServiceImpl implements TeamMatchRequestService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamMatchRequestJPARepository teamMatchRequestJPARepository;
    private final TeamMatchJPARepository teamMatchJPARepository;
    private final UserJPARepository userJPARepository;

    @Autowired
    public TeamMatchRequestServiceImpl(TeamMemberRepository teamMemberRepository,
                                       TeamMatchRequestJPARepository teamMatchRequestJPARepository,
                                       TeamMatchJPARepository teamMatchJPARepository,
                                       UserJPARepository userJPARepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamMatchRequestJPARepository = teamMatchRequestJPARepository;
        this.teamMatchJPARepository = teamMatchJPARepository;
        this.userJPARepository = userJPARepository;
    }

    @Override
    public void requestTeamMatch(Long userId, TeamMatchRequestDTO request) {
        // 해당 유저가 자신의 팀에 속해 있는지 확인
        int isUserInHisTeam = teamMemberRepository.isTeamMember(request.getAwayTeamId(), userId);

        if (isUserInHisTeam == 0) {
            throw new IllegalArgumentException("The user is not a member of his team.");
        }

        // 해당 유저가 이 매치를 신청할 수 있는지 확인 (예: away team의 팀장 혹은 home team의 팀장 등 필요한 조건 체크)
        Optional<TeamMatch> optionalTeamMatch = teamMatchJPARepository.findValidMatchById(request.getAwayTeamId());

        if(!optionalTeamMatch.isPresent()){
            throw new IllegalArgumentException("The match has not been fully set up by the leaders yet.");
        }

        // User와 TeamMatch 엔터티 조회
        User user = userJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID " + userId));

        TeamMatch teamMatch = teamMatchJPARepository.findById(request.getMatchId())
                .orElseThrow(() -> new IllegalArgumentException("No team match found with ID " + request.getMatchId()));

        // DTO to Entity 변환
        TeamMatchRequest teamMatchRequest = TeamMatchRequest.builder()
                .user(user)
                .teamMatch(teamMatch)
                .build();

        // DB에 저장
        teamMatchRequestJPARepository.save(teamMatchRequest);
    }
}