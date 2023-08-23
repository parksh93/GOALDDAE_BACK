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

    // 동적테이블 생성 - 팀매치
    @Override
    public boolean createMatchTeamTable(Long fieldId) {
        try {
            String safeTable = MyBatisUtil.safeTable(fieldId);
            matchTeamRepository.createMatchTeamTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    // 동적테이블 생성 - 개인매치
    @Override
    public boolean createMatchIndividualTable(Long fieldId) {
        try {
            String safeTable = MyBatisUtil.safeTable(fieldId);
            matchIndividualRepository.createMatchIndividualTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            return false;
        }
    }

    // 동적테이블 삭제 - 팀매치

    @Override
    public boolean dropMatchTeamTable(Long fieldId) {
        try {
            String safeTable = MyBatisUtil.safeTable(fieldId);
            matchTeamRepository.dropMatchTeamTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 삭제 중 오류가 발생하였습니다: " + e.getMessage());
            return false;
        }
    }

    // 동적 테이블 삭제 - 개인 매치

    @Override
    public boolean dropMatchIndividualTable(Long fieldId){
        try{
            String safeTable = MyBatisUtil.safeTable(fieldId);
            matchIndividualRepository.dropMatchIndividualTable(safeTable);
            return true;
        }catch(Exception e){
            System.out.println("delete table error : " + e.getMessage());
            return false;
        }
    }

    // 외래 키 추가 코드
    @Override
    public void addForeignKeyConstraintToMatchTeam(Long fieldId) {
        matchTeamRepository.addForeignKeyConstraintToMatchTeam(fieldId);
    }

    @Override
    public void addForeignKeyConstraintToMatchIndividual(Long fieldId) {
        matchIndividualRepository.addForeignKeyConstraintToMatchIndividual(fieldId);
    }
}
