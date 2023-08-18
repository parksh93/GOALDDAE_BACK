//package com.goalddae.service;
//
//import com.goalddae.repository.MatchRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
// 아직 동적테이블 생성이 안돼서, 미리 매치 리스트 조회 기능 만들어 놓음 -유정원
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
