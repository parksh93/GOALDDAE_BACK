//package com.goalddae.service;
//
//import com.goalddae.repository.MatchRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public interface MatchService {
//
//    MatchRepository matchRepository;
//
//    @Autowired
//    public MatchService(MatchRepository matchRepository) {
//        this.matchRepository = matchRepository;
//    }
//
//    public List<Match> findMatchById(long userId) {
//        return MatchRepository.findAllByuserId(userId);
//    }
//}
