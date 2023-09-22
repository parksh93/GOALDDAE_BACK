package com.goalddae.repository;

import com.goalddae.dto.team.TeamMemberCheckDTO;
import com.goalddae.dto.team.TeamMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMemberRepository {
    void createTeamMemberTable(@Param("teamId") Long teamId);
    List<TeamMemberCheckDTO> findAllTeamMembersByTeamId(long teamId);
    TeamMemberCheckDTO findByUserId(long userId, long teamId);
    int findTeamManagerByUserId(long userId, long teamId);
    void addTeamMember(TeamMemberDTO teamMemberDTO);
    void deleteMemberByUserId(TeamMemberDTO teamMemberDTO);
}
