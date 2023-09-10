package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.dto.team.TeamMemberListDTO;
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
    public List<TeamMemberListDTO> findAllTeamMember() {
        return teamMemberRepository.findAllTeamMember();
    }

    @Override
    public TeamMemberDTO findById(long id) {
        return teamMemberRepository.findById(id);
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
    public void deleteTeamMemberById(long id) {
        teamMemberRepository.deleteTeamMemberById(id);
    }
}
