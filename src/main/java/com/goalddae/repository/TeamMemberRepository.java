package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TeamMemberRepository {
    void createTeamMemberTable(@Param("teamId") Long teamId);
    // 팀 매치 신청 전 팀 유저인지 확인 쿼리
    @Select("SELECT COUNT(*) FROM team_member_#{teamId} WHERE users_id = #{userId}")
    int isTeamMember(@Param("teamId") Long teamId, @Param("userId") Long userId);
}
