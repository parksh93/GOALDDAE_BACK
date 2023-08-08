package com.goalddae.service;

import com.goalddae.entity.Match;
import com.goalddae.repository.UserMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMatchService {

    UserMatchRepository userMatchRepository;

    @Autowired
    public UserMatchService(UserMatchRepository userMatchRepository) {
        this.userMatchRepository = userMatchRepository;
    }

    public List<Match> findMatchById(long userId) {
       return userMatchRepository.findMatchById(userId);
    }
}
