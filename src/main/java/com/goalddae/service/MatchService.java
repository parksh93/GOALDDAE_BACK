package com.goalddae.service;

public interface MatchService {
    boolean createMatchTeamTable(Long matchTeamId);
    boolean createMatchIndividualTable(Long matchIndividualId);
    boolean dropMatchTeamTable(Long matchTeamId);
    boolean dropMatchIndividualTable(Long matchIndividualId);

    // 외래키 추가 코드
    void addForeignKeyConstraintToMatchTeam(Long fieldId);
    void addForeignKeyConstraintToMatchIndividual(Long fieldId);
}