package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.repository.TeamMemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamMemberServiceImpl implements TeamMemberService{


    private TeamMemberRepository teamMemberRepository;

    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository){
        this.teamMemberRepository = teamMemberRepository;
    }

    @Override
    public List<TeamMemberCheckDTO> findAllTeamMembersByTeamId(long teamId) {
        return teamMemberRepository.findAllTeamMembersByTeamId(teamId);
    }

    @Override
    public int findTeamManagerByUserId(long userId, long teamId) {
        return teamMemberRepository.findTeamManagerByUserId(userId, teamId);
    }

    @Override
    public void addTeamMember(TeamMemberDTO teamMemberDTO) {
        long teamId = teamMemberDTO.getTeamId();
        long userId = teamMemberDTO.getUserId();
        int teamManager = teamMemberDTO.getTeamManager();

        TeamMemberDTO newMember = new TeamMemberDTO();
        newMember.setTeamId(teamId);
        newMember.setUserId(userId);
        newMember.setTeamManager(teamManager);    // 팀원
        newMember.setUserJoinDate(LocalDateTime.now());

        teamMemberRepository.addTeamMember(newMember);
    }

    @Override
    public void deleteMemberByUserId(long usersId) {
        teamMemberRepository.deleteMemberByUserId(usersId);
    }
}
