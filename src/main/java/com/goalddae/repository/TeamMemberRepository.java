package com.goalddae.repository;

import com.goalddae.dto.team.TeamMemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeamMemberRepository {
    void createTeamMemberTable(@Param("teamId") Long teamId);

    List<TeamMemberDTO> findByTeamIdMember(long teamId);

    void addTeamMember(TeamMemberDTO teamMemberDTO);

    void deleteByUserIdMember(long usersId);


}
