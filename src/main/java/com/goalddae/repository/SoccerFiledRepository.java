package com.goalddae.repository;

import com.goalddae.entity.SoccerFiled;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoccerFiledRepository extends JpaRepository<SoccerFiled, Long> {
    List<SoccerFiled> findByRegion(String keyword);

}
