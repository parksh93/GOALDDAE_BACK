package com.goalddae.service;

import com.goalddae.dto.team.TeamListDTO;
import com.goalddae.entity.Team;
import com.goalddae.repository.TeamJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Team newTeam = Team.builder()
                .teamName(team.getTeamName())
                .area(team.getArea())
                .averageAge(team.getAverageAge())
                .teamIntroduce(team.getTeamIntroduce())
                .entryFee(team.getEntryFee())
                .entryGender(team.getEntryGender())
                .teamProfileImgUrl(team.getTeamProfileImgUrl())
                .preferredDay(team.getPreferredDay())
                .preferredTime(team.getPreferredTime())
                .build();
        teamJPARepository.save(newTeam);
    }

    @Override
    public void update(Team team) {
        Team newTeam = teamJPARepository.findTeamById(team.getId());

        newTeam = Team.builder()
                .teamName(team.getTeamName())
                .area(team.getArea())
                .averageAge(team.getAverageAge())
                .teamIntroduce(team.getTeamIntroduce())
                .entryFee(team.getEntryFee())
                .entryGender(team.getEntryGender())
                .teamProfileImgUrl(team.getTeamProfileImgUrl())
                .preferredDay(team.getPreferredDay())
                .preferredTime(team.getPreferredTime())
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
    public List findByArea(String area) {
        return teamJPARepository.findByArea(area);
    }

    @Override
    public List findByRecruiting(boolean recruiting) {
        return teamJPARepository.findByRecruiting(recruiting);
    }

    @Override
    public List findByAreaAndRecruiting(String area, boolean recruiting) {
        return teamJPARepository.findByAreaAndRecruiting(area, recruiting);
    }
}
