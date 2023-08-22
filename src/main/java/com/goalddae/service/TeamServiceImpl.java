package com.goalddae.service;

import com.goalddae.entity.Team;
import com.goalddae.repository.TeamApplyRepository;
import com.goalddae.repository.TeamJPARepository;
import com.goalddae.repository.TeamMatchResultRepository;
import com.goalddae.repository.TeamMemberRepository;
import com.goalddae.util.MyBatisUtil;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamJPARepository teamJPARepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamApplyRepository teamApplyRepository;
    private final TeamMatchResultRepository teamMatchResultRepository;

    public TeamServiceImpl(TeamJPARepository teamJPARepository,
                           TeamMemberRepository teamMemberRepository,
                           TeamApplyRepository teamApplyRepository,
                           TeamMatchResultRepository teamMatchResultRepository,
                           TeamMatchResultRepository matchResultRepository) {
        this.teamJPARepository = teamJPARepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamApplyRepository = teamApplyRepository;
        this.teamMatchResultRepository = teamMatchResultRepository;
    }

    @Override
    public void createTeamMemberTable(String teamMember) {
        String safeTable = MyBatisUtil.safeTable(teamMember);
        teamMemberRepository.createTeamMemberTable(safeTable);
    }

    @Override
    public void createTeamApplyTable(String teamApply) {
        String safeTable = MyBatisUtil.safeTable(teamApply);
        teamApplyRepository.createTeamApplyTable(safeTable);
    }

    @Override
    public void createTeamMatchResult(String teamMatchResult) {
        String safeTable = MyBatisUtil.safeTable(teamMatchResult);
        teamMatchResultRepository.createTeamMatchResultTable(safeTable);
    }

    @Override
    public void save(Team team) {
        Team newTeam = Team.builder()
                .teamName(team.getTeamName())
                .area(team.getArea())
                .averageAge(team.getAverageAge())
                .entryFee(team.getEntryFee())
                .entryGender(team.getEntryGender())
                .teamProfileImgUrl(team.getTeamProfileImgUrl())
                .preferredDay(team.getPreferredDay())
                .preferredTime(team.getPreferredTime())
                .build();
        teamJPARepository.save(newTeam);

        // 팀 이름을 가져와 테이블 생성에 사용
        String teamName = newTeam.getTeamName();

        // 동적테이블 생성
        this.createTeamApplyTable(teamName);
        this.createTeamMemberTable(teamName);
        this.createTeamMatchResult(teamName);
    }
}
