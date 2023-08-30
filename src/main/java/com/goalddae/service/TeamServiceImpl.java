package com.goalddae.service;

import com.goalddae.dto.team.TeamSaveDTO;
import com.goalddae.entity.Team;
import com.goalddae.repository.TeamApplyRepository;
import com.goalddae.repository.TeamJPARepository;
import com.goalddae.repository.TeamMatchResultRepository;
import com.goalddae.repository.TeamMemberRepository;
import com.goalddae.util.MyBatisUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamJPARepository teamJPARepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamApplyRepository teamApplyRepository;
    private final TeamMatchResultRepository teamMatchResultRepository;

    public TeamServiceImpl(TeamJPARepository teamJPARepository,
                           TeamMemberRepository teamMemberRepository,
                           TeamApplyRepository teamApplyRepository,
                           TeamMatchResultRepository teamMatchResultRepository) {
        this.teamJPARepository = teamJPARepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamApplyRepository = teamApplyRepository;
        this.teamMatchResultRepository = teamMatchResultRepository;
    }

    // 동적테이블 생성 - 팀 멤버
    @Override
    public boolean createTeamMemberTable(@Param("teamId") Long teamId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(teamId);
            teamMemberRepository.createTeamMemberTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace(); // 예외 객체를 출력
            return false;
        }
    }

    // 동적테이블 생성 - 팀 수락
    @Override
    public boolean createTeamApplyTable(@Param("teamId") Long teamId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(teamId);
            teamApplyRepository.createTeamApplyTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace(); // 예외 객체를 출력
            return false;
        }
    }

    // 동적테이블 생성 - 팀 경기 결과
    @Override
    public boolean createTeamMatchResult(@Param("teamId") Long teamId) {
        try {
            Long safeTable = MyBatisUtil.safeTable(teamId);
            teamMatchResultRepository.createTeamMatchResultTable(safeTable);
            return true;
        } catch (Exception e) {
            System.out.println("테이블 생성 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace(); // 예외 객체를 출력
            return false;
        }
    }

    @Override
    public void save(TeamSaveDTO teamSaveDTO) {
        Team newTeam = Team.builder()
                .teamName(teamSaveDTO.getTeamName())
                .area(teamSaveDTO.getArea())
                .entryFee(teamSaveDTO.getEntryFee())
                .preferredDay(teamSaveDTO.getPreferredDay())
                .preferredTime(teamSaveDTO.getPreferredTime())
                .averageAge(teamSaveDTO.getAverageAge())
                .entryGender(teamSaveDTO.getEntryGender())
                .recruiting(teamSaveDTO.getRecruiting())
                .teamCreate(LocalDateTime.now())
                .teamProfileImgUrl("default_profile_img_url")
                .build();

        teamJPARepository.save(newTeam);

        // 팀 ID 필드를 가져와 테이블 생성에 F사용
        Long id = newTeam.getId();

        // 동적테이블 생성
        this.createTeamApplyTable(id);
        this.createTeamMemberTable(id);
        this.createTeamMatchResult(id);
    }
}
