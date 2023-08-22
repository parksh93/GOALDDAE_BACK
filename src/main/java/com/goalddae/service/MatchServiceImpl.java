package com.goalddae.service;

import com.goalddae.repository.MatchIndividualRepository;
import com.goalddae.repository.MatchTeamRepository;
import com.goalddae.util.MyBatisUtil;
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
        String safeTable = MyBatisUtil.safeTable(matchTeam);
        matchTeamRepository.createMatchTeamTable(safeTable);
    }

    @Override
    public void createMatchIndividualTable(String matchIndividual) {
        String safeTable = MyBatisUtil.safeTable(matchIndividual);
        matchIndividualRepository.createMatchIndividualTable(safeTable);
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

