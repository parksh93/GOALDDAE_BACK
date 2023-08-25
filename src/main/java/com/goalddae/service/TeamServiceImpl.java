package com.goalddae.service;

import com.goalddae.repository.TeamApplyRepository;
import com.goalddae.repository.TeamMatchResultRepository;
import com.goalddae.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamApplyRepository teamApplyRepository;

    private final TeamMatchResultRepository teamMatchResultRepository;

    @Autowired
    public TeamServiceImpl(TeamMemberRepository teamMemberRepository,
                           TeamApplyRepository teamApplyRepository,
                           TeamMatchResultRepository teamMatchResultRepository) {
        this.teamMemberRepository = teamMemberRepository;
        this.teamApplyRepository = teamApplyRepository;
        this.teamMatchResultRepository = teamMatchResultRepository;
    }

    @Override
    @Transactional
    public void createTeamMemberTable(String teamMember) {
        teamMemberRepository.createTeamMemberTable(teamMember);
    }

    @Override
    @Transactional
    public void createTeamApplyTable(String teamApply) {
        teamApplyRepository.createTeamApplyTable(teamApply);
    }

    @Override
    @Transactional
    public void createTeamMatchResult(String teamMatchResult) {
        teamMatchResultRepository.createTeamMatchResultTable(teamMatchResult);
    }
}
