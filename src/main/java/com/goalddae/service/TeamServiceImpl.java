package com.goalddae.service;

import com.goalddae.dto.team.*;
import com.goalddae.dto.user.ChangeUserInfoDTO;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.entity.Team;
import com.goalddae.entity.User;
import com.goalddae.repository.*;
import com.goalddae.util.S3Uploader;
import org.springframework.stereotype.Service;

import com.goalddae.util.MyBatisUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamJPARepository teamJPARepository;
    private final TeamMemberRepository teamMemberRepository;
    private final TeamApplyRepository teamApplyRepository;
    private final TeamMatchResultRepository teamMatchResultRepository;
    private final UserJPARepository userJPARepository;
    private final S3Uploader s3Uploader;



    public TeamServiceImpl(TeamJPARepository teamJPARepository,
                           TeamMemberRepository teamMemberRepository,
                           TeamApplyRepository teamApplyRepository,
                           TeamMatchResultRepository teamMatchResultRepository,
                           UserJPARepository userJPARepository,
                           S3Uploader s3Uploader) {
        this.teamJPARepository = teamJPARepository;
        this.teamMemberRepository = teamMemberRepository;
        this.teamApplyRepository = teamApplyRepository;
        this.teamMatchResultRepository = teamMatchResultRepository;
        this.userJPARepository = userJPARepository;
        this.s3Uploader = s3Uploader;

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
        Team updateTeam = teamJPARepository.findTeamById(teamUpdateDTO.getId());

        updateTeam = Team.builder()
                .id(updateTeam.getId())
                .teamName(teamUpdateDTO.getTeamName())
                .area(teamUpdateDTO.getArea())
                .averageAge(teamUpdateDTO.getAverageAge())
                .teamIntroduce(teamUpdateDTO.getTeamIntroduce())
                .entryFee(teamUpdateDTO.getEntryFee())
                .entryGender(teamUpdateDTO.getEntryGender())
                .preferredDay(teamUpdateDTO.getPreferredDay())
                .preferredTime(teamUpdateDTO.getPreferredTime())
                .teamCreate(updateTeam.getTeamCreate())
                .teamProfileUpdate(LocalDateTime.now())
                .build();

        teamJPARepository.save(updateTeam);
    }

    @Override
    public void updateTeamProfileImg(TeamUpdateDTO teamUpdateDTO, MultipartFile multipartFile) {
        Team updateTeam = teamJPARepository.findTeamById(teamUpdateDTO.getId());

        String uploadImgeUrl = null;
        try {
            uploadImgeUrl = s3Uploader.uploadFiles(multipartFile, "profile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        updateTeam = Team.builder()
                .teamProfileImgUrl(uploadImgeUrl)
                .build();

        teamJPARepository.save(updateTeam);

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
    public List<TeamMemberCheckDTO> findStatus0ByTeamId(long teamId) {
        return teamApplyRepository.findStatus0ByTeamId(teamId);
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

    @Transactional
    @Override
    public void acceptApply(TeamAcceptApplyDTO teamAcceptApplyDTO) {

        try {
            // acceptStatus 수락(1) 업데이트
            long userId = teamAcceptApplyDTO.getTeamApplyDTO().getUserId();
            long teamId = teamAcceptApplyDTO.getTeamApplyDTO().getTeamId();

            TeamApplyDTO acceptApply = TeamApplyDTO.builder()
                    .userId(userId)
                    .teamId(teamId)
                    .teamAcceptStatus(1)
                    .build();

            teamApplyRepository.updateAcceptStatus(acceptApply);

            // 팀멤버 추가
            long memberTeamId = teamAcceptApplyDTO.getTeamMemberDTO().getTeamId();
            long memberUserId = teamAcceptApplyDTO.getTeamMemberDTO().getUserId();

            TeamMemberDTO newMember = TeamMemberDTO.builder()
                    .teamId(memberTeamId)
                    .userId(memberUserId)
                    .teamManager(1)
                    .userJoinDate(LocalDateTime.now())
                    .build();

            teamMemberRepository.addTeamMember(newMember);

            // 유저 teamId 변경
            User user = userJPARepository.findById(teamAcceptApplyDTO.getGetUserInfoDTO().getId()).get();

            if (user != null) {
                ChangeUserInfoDTO changeUserInfoDTO = new ChangeUserInfoDTO(user);
                changeUserInfoDTO.setTeamId(teamAcceptApplyDTO.getGetUserInfoDTO().getTeamId());
                user = changeUserInfoDTO.toEntity();

                userJPARepository.save(user);
            }
        } catch (Exception e) {
            throw new RuntimeException("트랜잭션 처리 실패", e);
        }
    }

    @Override
    public void rejectApply(TeamApplyDTO teamApplyDTO) {
        // acceptStatus 거절(2) 업데이트
        long userId = teamApplyDTO.getUserId();
        long teamId = teamApplyDTO.getTeamId();

        TeamApplyDTO rejectApply = TeamApplyDTO.builder()
                .userId(userId)
                .teamId(teamId)
                .teamAcceptStatus(2)
                .build();

        teamApplyRepository.updateAcceptStatus(rejectApply);
    }
}
