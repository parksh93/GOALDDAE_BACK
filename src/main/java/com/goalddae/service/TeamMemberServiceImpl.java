package com.goalddae.service;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.team.TeamMemberDTO;
import com.goalddae.dto.user.ChangeUserInfoDTO;
import com.goalddae.entity.User;
import com.goalddae.repository.TeamMemberRepository;
import com.goalddae.repository.UserJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamMemberServiceImpl implements TeamMemberService{


    private TeamMemberRepository teamMemberRepository;
    private UserJPARepository userJPARepository;

    public TeamMemberServiceImpl(TeamMemberRepository teamMemberRepository,
                                 UserJPARepository userJPARepository){
        this.teamMemberRepository = teamMemberRepository;
        this.userJPARepository = userJPARepository;
    }

    @Override
    public List<TeamMemberCheckDTO> findAllTeamMembersByTeamId(long teamId) {
        return teamMemberRepository.findAllTeamMembersByTeamId(teamId);
    }

    @Override
    public TeamMemberCheckDTO findByUserId(long userId, long teamId) {
        return teamMemberRepository.findByUserId(userId, teamId);
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

    @Transactional
    @Override
    public void removeTeamMember(TeamMemberDTO teamMemberDTO) {

        try{
            // 팀 멤버 삭제
            teamMemberRepository.deleteMemberByUserId(teamMemberDTO);

            // 유저 teamId 변경
            User user = userJPARepository.findById(teamMemberDTO.getUserId()).get();

            if (user != null) {
                ChangeUserInfoDTO changeUserInfoDTO = new ChangeUserInfoDTO(user);
                changeUserInfoDTO.setTeamId(null);
                user = changeUserInfoDTO.toEntity();

                userJPARepository.save(user);
            }

        } catch (Exception e){
            throw new RuntimeException("트랜잭션 처리 실패", e);
        }
    }

}
