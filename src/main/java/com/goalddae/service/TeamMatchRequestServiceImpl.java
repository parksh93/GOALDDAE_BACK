package com.goalddae.service;

import com.goalddae.dto.match.TeamMatchRequestDTO;
import com.goalddae.dto.user.TeamMatchUserInfoDTO;
import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;
import com.goalddae.entity.User;
import com.goalddae.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TeamMatchRequestServiceImpl implements TeamMatchRequestService {

    private final TeamJPARepository teamJPARepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamMatchRequestJPARepository teamMatchRequestJPARepository;
    private final TeamMatchJPARepository teamMatchJPARepository;
    private final UserJPARepository userJPARepository;

    @Autowired
    public TeamMatchRequestServiceImpl(TeamMemberRepository teamMemberRepository,
                                       TeamMatchRequestJPARepository teamMatchRequestJPARepository,
                                       TeamMatchJPARepository teamMatchJPARepository,
                                       UserJPARepository userJPARepository,
                                       TeamJPARepository teamJPARepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamMatchRequestJPARepository = teamMatchRequestJPARepository;
        this.teamMatchJPARepository = teamMatchJPARepository;
        this.userJPARepository = userJPARepository;
        this.teamJPARepository = teamJPARepository;
    }

    // 팀 매치 신청
    @Override
    public void requestTeamMatch(Long userId, Long teamMatchId, TeamMatchRequestDTO request) {
        // 해당 유저가 자신의 팀에 속해 있는지 확인
        int isUserInHisTeam = teamMemberRepository.isTeamMember(request.getAwayTeamId(), userId);

        if (isUserInHisTeam == 0) {
            throw new IllegalArgumentException("The user is not a member of his team.");
        }

        // 해당 유저가 이 매치를 신청할 수 있는지 확인 (예: away team의 팀장 혹은 home team의 팀장 등 필요한 조건 체크)
        Optional<TeamMatch> optionalTeamMatch = teamMatchJPARepository.findValidMatchById(teamMatchId);

        if(!optionalTeamMatch.isPresent()){
            throw new IllegalArgumentException("The match has not been fully set up by the leaders yet.");
        }

        // User와 TeamMatch 엔터티 조회
        User user = userJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with ID " + userId));

        TeamMatch teamMatch = teamMatchJPARepository.findById(teamMatchId)
                .orElseThrow(() -> new IllegalArgumentException("No match found with ID " + teamMatchId));

        // 어웨이 팀 정보 설정
        teamMatch.applyAway(user, request.getAwayTeamId());

        // DB에서 변경된 매치 정보 저장
        teamMatchJPARepository.save(teamMatch);
    }

    @Override
    public List<TeamMatchUserInfoDTO> getHomeRequest(Long matchId) {
        List<User> homeApplications = getApplications(matchId, true);
        return homeApplications.stream()
                .map(user -> new TeamMatchUserInfoDTO(user.getId(), user.getTeamId(), user.getNickname(), user.getProfileImgUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamMatchUserInfoDTO> getAwayRequest(Long matchId) {
        List<User> awayApplications = getApplications(matchId, false);
        return awayApplications.stream()
                .map(user -> new TeamMatchUserInfoDTO(user.getId(), user.getTeamId(), user.getNickname(), user.getProfileImgUrl()))
                .collect(Collectors.toList());
    }

    // 주어진 매치 ID와 팀 타입(홈/어웨이)으로 해당 팀의 모든 신청자 목록을 조회
    private List<User> getApplications(Long matchId, boolean isHome) {
        TeamMatch match = teamMatchJPARepository.findById(matchId).orElseThrow(NoSuchElementException::new);
        Long teamId = isHome ? match.getHomeUser().getId() : match.getAwayUser().getId();
        User teamLeader = isHome ? match.getHomeUser() : match.getAwayUser();

        List<TeamMatchRequest> request = teamMatchRequestJPARepository.findByTeamIdAndTeamMatchId(teamId, matchId);

        return Stream.concat(
                        Stream.of(teamLeader),
                        request.stream().map(TeamMatchRequest::getUser))
                .collect(Collectors.toList());
    }
}