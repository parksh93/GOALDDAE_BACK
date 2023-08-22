package com.goalddae.service;

import com.goalddae.repository.MatchIndividualRepository;
import com.goalddae.repository.MatchTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchServiceImpl implements MatchService{

    private final MatchTeamRepository matchTeamRepository;
    private final MatchIndividualRepository matchIndividualRepository;

    @Autowired
    public MatchServiceImpl(MatchTeamRepository matchTeamRepository,
                            MatchIndividualRepository matchIndividualRepository) {
        this.matchTeamRepository = matchTeamRepository;
        this.matchIndividualRepository = matchIndividualRepository;
    }

    @Override
    @Transactional
    public void createMatchTeamTable(String matchTeam) {
        matchTeamRepository.createMatchTeamTable(matchTeam);
    }

    @Override
    public void createMatchIndividualTable(String matchIndividual) {
        matchIndividualRepository.createMatchIndividualTable(matchIndividual);
    }
}
