package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamMemberRepository {
    void createTeamMemberTable(String teamMember);
}
