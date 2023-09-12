package com.goalddae.service;

import com.goalddae.dto.team.TeamApplyDTO;
import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.dto.team.TeamUpdateDTO;
import com.goalddae.entity.Team;
import com.goalddae.repository.TeamJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goalddae.dto.team.TeamSaveDTO;
import com.goalddae.repository.TeamApplyRepository;
import com.goalddae.repository.TeamMatchResultRepository;
import com.goalddae.repository.TeamMemberRepository;
import com.goalddae.util.MyBatisUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List findAll() {
        return teamJPARepository.findAll();
    }

    @Override
    public Team findTeamById(Long id) {
        return teamJPARepository.findTeamById(id);
    }

    /* @Override
    public void save(Team team) {
        teamJPARepository.save(team);
    } */

    @Override
    public void update(TeamUpdateDTO teamUpdateDTO) {
        Team newTeam = teamJPARepository.findTeamById(teamUpdateDTO.getId());

        newTeam = Team.builder()
                .id(newTeam.getId())
                .teamName(teamUpdateDTO.getTeamName())
                .area(teamUpdateDTO.getArea())
                .averageAge(teamUpdateDTO.getAverageAge())
                .teamIntroduce(teamUpdateDTO.getTeamIntroduce())
                .entryFee(teamUpdateDTO.getEntryFee())
                .entryGender(teamUpdateDTO.getEntryGender())
                .teamProfileImgUrl(teamUpdateDTO.getTeamProfileImgUrl())
                .preferredDay(teamUpdateDTO.getPreferredDay())
                .preferredTime(teamUpdateDTO.getPreferredTime())
                .teamCreate(newTeam.getTeamCreate())
                .teamProfileUpdate(LocalDateTime.now())
                .build();

        teamJPARepository.save(newTeam);
    }

    @Override
    public void deleteTeamById(Long id) {
        teamJPARepository.deleteTeamById(id);
    }

    @Override
    public List<Team> findByTeamName(String searchTerm) {
        return teamJPARepository.findByTeamName(searchTerm);
    }

    @Override
    public List<TeamListDTO> findByArea(String area) {
        List<Team> result = teamJPARepository.findByArea(area);

        return result.stream()
                .map(TeamListDTO::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeamListDTO> findByRecruiting(boolean recruiting) {
        List<Team> result = teamJPARepository.findByRecruiting(recruiting);

        return result.stream()
                .map(TeamListDTO::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<TeamListDTO> findByAreaAndRecruiting(String area, boolean recruiting) {
        List<Team> result = teamJPARepository.findByAreaAndRecruiting(area, recruiting);

        return result.stream()
                .map(TeamListDTO::toDTO)
                .collect(Collectors.toList());
    }

    // 동적테이블 생성 - 팀 멤버
    @Override
    public boolean createTeamMemberTable (@Param("teamId") Long teamId){
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
    public boolean createTeamApplyTable (@Param("teamId") Long teamId){
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
    public boolean createTeamMatchResult (@Param("teamId") Long teamId){
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
    public void save(TeamSaveDTO teamSaveDTO){
        Team newTeam = Team.builder()
                .teamName(teamSaveDTO.getTeamName())
                .area(teamSaveDTO.getArea())
                .entryFee(teamSaveDTO.getEntryFee())
                .recruiting(true)
                .preferredDay(teamSaveDTO.getPreferredDay())
                .preferredTime(teamSaveDTO.getPreferredTime())
                .averageAge(teamSaveDTO.getAverageAge())
                .entryGender(teamSaveDTO.getEntryGender())
                .teamCreate(LocalDateTime.now())
                .teamProfileImgUrl("default_profile_img_url")
                .build();

        teamJPARepository.save(newTeam);

        // 팀 ID 필드를 가져와 테이블 생성에 사용
        Long id = newTeam.getId();

        // 동적테이블 생성
        this.createTeamApplyTable(id);
        this.createTeamMemberTable(id);
        this.createTeamMatchResult(id);
    }

    @Override
    public Long getAutoIncrementValue() {
        Team lastInsertedTeam = teamJPARepository.findFirstByOrderByIdDesc();

        if(lastInsertedTeam != null ){
            return lastInsertedTeam.getId();
        } else {
            return null;
        }
    }



    @Override
    public List<TeamApplyDTO> findAllApplyByTeamId(long teamId) {
        return teamApplyRepository.findAllApplyByTeamId(teamId);
    }

    @Override
    public TeamApplyDTO findApplyById(long id, long teamId) {
        return teamApplyRepository.findApplyById(id, teamId);
    }

    @Override
    public void addTeamApply(TeamApplyDTO teamApplyDTO) {
        long teamId = teamApplyDTO.getTeamId();
        long userId = teamApplyDTO.getUserId();

        TeamApplyDTO newApply = new TeamApplyDTO();
        newApply.setTeamId(teamId);
        newApply.setUserId(userId);
        newApply.setTeamAcceptStatus(0);
        newApply.setTeamApplyDate(LocalDateTime.now());

        teamApplyRepository.addTeamApply(newApply);
    }

    @Override
    public void updateAcceptStatus(TeamApplyDTO teamApplyDTO) {
        long id = teamApplyDTO.getId();
        long teamId = teamApplyDTO.getTeamId();
        int teamAcceptStatus = teamApplyDTO.getTeamAcceptStatus();

        TeamApplyDTO apply = TeamApplyDTO.builder()
                .id(id)
                .teamId(teamId)
                .teamAcceptStatus(teamAcceptStatus)
                .build();

        teamApplyRepository.updateAcceptStatus(apply);
    }
}
