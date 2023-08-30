package com.goalddae.service;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.dto.team.TeamUpdateDTO;
import com.goalddae.entity.Team;
import com.goalddae.repository.TeamJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService{

    private final TeamJPARepository teamJPARepository;

    @Autowired
    public TeamServiceImpl(TeamJPARepository teamJPARepository){
        this.teamJPARepository = teamJPARepository;
    }

    @Override
    public List findAll() {
        return teamJPARepository.findAll();
    }

    @Override
    public Team findTeamById(Long id) {
        return teamJPARepository.findTeamById(id);
    }

    @Override
    public void save(Team team) {
        teamJPARepository.save(team);
    }

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
}
