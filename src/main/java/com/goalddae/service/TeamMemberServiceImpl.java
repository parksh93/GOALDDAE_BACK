package com.goalddae.service;

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
    public List<TeamMemberDTO> findByTeamIdMember(long teamId) {
        return teamMemberRepository.findByTeamIdMember(teamId);
    }

    @Override
    public void addTeamMember(TeamMemberDTO teamMemberDTO) {
        long teamId = teamMemberDTO.getTeamId();
        long userId = teamMemberDTO.getUserId();

        TeamMemberDTO newMember = new TeamMemberDTO();
        newMember.setTeamId(teamId);
        newMember.setUserId(userId);
        newMember.setUserJoinDate(LocalDateTime.now());

        teamMemberRepository.addTeamMember(newMember);
    }

    @Override
    public void deleteByUserIdMember(long usersId) {
        teamMemberRepository.deleteByUserIdMember(usersId);
    }
}
