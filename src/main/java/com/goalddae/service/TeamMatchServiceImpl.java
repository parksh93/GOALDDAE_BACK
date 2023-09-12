package com.goalddae.service;

import com.goalddae.entity.TeamMatch;
import com.goalddae.entity.TeamMatchRequest;
import com.goalddae.repository.TeamMatchJPARepository;
import com.goalddae.repository.TeamMatchRequestJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMatchServiceImpl implements TeamMatchService {

    private TeamMatchJPARepository teamMatchJPARepository;

    private TeamMatchRequestJPARepository teamMatchRequestJPARepository;

    @Autowired
    public TeamMatchServiceImpl(TeamMatchJPARepository teamMatchJPARepository,
                                TeamMatchRequestJPARepository teamMatchRequestJPARepository) {
        this.teamMatchJPARepository = teamMatchJPARepository;
        this.teamMatchRequestJPARepository = teamMatchRequestJPARepository;
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
}
