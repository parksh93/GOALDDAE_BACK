package com.goalddae.service;

import com.goalddae.dto.match.MatchIndividualDTO;
import com.goalddae.repository.MatchIndividualRepository;
import com.goalddae.repository.MatchTeamRepository;
import com.goalddae.util.MyBatisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchTeamRepository matchTeamRepository;
    private final MatchIndividualRepository matchIndividualRepository;

    @Autowired
    public MatchServiceImpl(MatchTeamRepository matchTeamRepository,
                            MatchIndividualRepository matchIndividualRepository) {
        this.matchTeamRepository = matchTeamRepository;
        this.matchIndividualRepository = matchIndividualRepository;
    }

    // 팀 매치 테이블 생성 메소드
    @Override
    @Transactional
    public void createMatchTeamTable(String matchTeam) {
        // MyBatisUtil.safeTable()을 호출하여 안전한 테이블 이름을 생성하고 저장
        String safeMatchTeam = MyBatisUtil.safeTable(matchTeam);

        // 안전한 테이블 이름을 사용하여 팀 매치 테이블 생성
        matchTeamRepository.createMatchTeamTable(safeMatchTeam);
    }

    // 개인 매치 테이블 생성 메소드
    @Override
    @Transactional
    public void createMatchIndividualTable(String matchIndividual) {
        String safeMatchIndividual = MyBatisUtil.safeTable(matchIndividual);
        matchIndividualRepository.createMatchIndividualTable(safeMatchIndividual);
    }

    // 팀 매치 테이블 생성 메소드
    @Override
    @Transactional
    public List<MatchIndividualDTO> findIndividualMatches(String matchIndividualTable,
                                                          LocalDateTime recruitStart,
                                                          LocalDateTime recruitEnd) {
        String safeMatchIndividualTable = MyBatisUtil.safeTable(matchIndividualTable);
        try {
            matchIndividualRepository.createMatchIndividualTable(safeMatchIndividualTable);
        } catch (Exception e) {
            // 테이블이 이미 존재하는 경우 무시하고 진행
            if (!e.getMessage().contains("already exists")) {
                throw e;
            }
        }
        return matchIndividualRepository.findIndividualMatches(safeMatchIndividualTable, recruitStart, recruitEnd);
    }
}

