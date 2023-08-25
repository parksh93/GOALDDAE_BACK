package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeamApplyRepository {
    void createTeamApplyTable(String teamApply);
}
