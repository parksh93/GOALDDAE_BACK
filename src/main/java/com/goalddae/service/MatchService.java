package com.goalddae.service;

public interface MatchService {
    void createMatchTeamTable(String matchTeam);
    void createMatchIndividualTable(String matchIndividual);

    // 외래키 추가 코드
    void addForeignKeyConstraintToMatchTeam(String fieldName);
    void addForeignKeyConstraintToMatchIndividual(String fieldName);

}