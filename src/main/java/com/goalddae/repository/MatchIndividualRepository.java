package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchIndividualRepository {
    void createMatchIndividualTable(String matchIndividual);
}
