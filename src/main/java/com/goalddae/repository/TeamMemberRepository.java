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

    // 팀 매치 신청 전 팀 유저인지 확인 쿼리
    @Select("SELECT COUNT(*) FROM team_member_#{teamId} WHERE users_id = #{userId}")
    int isTeamMember(@Param("teamId") Long teamId, @Param("userId") Long userId);
    // 팀장 여부 확인 쿼리
    @Select("SELECT COUNT(*) FROM team_member_#{teamId} WHERE team_manager = #{userId}")
    int isTeamLeader(@Param("teamId") Long teamId, @Param("userId") Long userId);
}
