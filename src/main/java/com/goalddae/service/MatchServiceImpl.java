package com.goalddae.service;

import com.goalddae.repository.MatchIndividualRepository;
import com.goalddae.repository.MatchTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchIndividualRepository matchIndividualRepository;
    private MatchTeamRepository matchTeamRepository;


    public MatchServiceImpl(MatchTeamRepository matchTeamRepository,
                            MatchIndividualRepository matchIndividualRepository) {
        this.matchTeamRepository = matchTeamRepository;
        this.matchIndividualRepository = matchIndividualRepository;
    }

    @Override
    public void createMatchTeamTable(String matchTeam) {
        matchTeamRepository.createMatchTeamTable(matchTeam);
    }

    @Override
    public void createMatchIndividualTable(String matchIndividual) {
        matchIndividualRepository.createMatchIndividualTable(matchIndividual);
    }

    // 외래키 추가 코드
    @Override
    public void addForeignKeyConstraintToMatchTeam(String fieldName) {
        matchTeamRepository.addForeignKeyConstraintToMatchTeam(fieldName);
    }

    // 외래키 추가 코드
    @Override
    public void addForeignKeyConstraintToMatchIndividual(String fieldName) {
        matchIndividualRepository.addForeignKeyConstraintToMatchIndividual(fieldName);
    }
}

