package com.goalddae.service;

import com.goalddae.dto.team.*;
import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.entity.Team;

import com.goalddae.entity.Team;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TeamService {

    List<TeamListDTO> findAll();
    Team findTeamById(Long id);
    //void save(Team team);
    void update(TeamUpdateDTO teamUpdateDTO);
    void updateTeamProfileImg(TeamUpdateDTO teamUpdateDTO, MultipartFile multipartFile);
    void deleteTeamById(Long id);
    List<Team> findByTeamName(String searchTerm);
    List<TeamListDTO> findByArea(String area);
    List<TeamListDTO> findByRecruiting(boolean recruiting);
    List<TeamListDTO> findByAreaAndRecruiting(String area, boolean recruiting);

    boolean createTeamMemberTable(@Param("teamId") Long teamId);
    boolean createTeamApplyTable(@Param("teamId") Long teamId);
    boolean createTeamMatchResult(@Param("teamId") Long teamId);
    void save(TeamSaveDTO teamSaveDTO);

    Long getAutoIncrementValue();


    List<TeamMemberCheckDTO> findStatus0ByTeamId(long teamId);
    TeamApplyDTO findApplyById(long id, long teamId);
    void addTeamApply(TeamApplyDTO teamApplyDTO);
    void acceptApply(TeamAcceptApplyDTO teamAcceptApplyDTO);
    void rejectApply(TeamApplyDTO teamApplyDTO);

}
