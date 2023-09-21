package com.goalddae.repository;

import com.goalddae.dto.team.TeamApplyDTO;
import com.goalddae.dto.team.TeamMemberCheckDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface TeamApplyRepository {
    void createTeamApplyTable(@Param("teamId") Long teamId);
    List<TeamMemberCheckDTO> findStatus0ByTeamId(long teamId);
    TeamApplyDTO findApplyById(long id, long teamId);
    void addTeamApply(TeamApplyDTO teamApplyDTO);
    void updateAcceptStatus(TeamApplyDTO teamApplyDTO);
}