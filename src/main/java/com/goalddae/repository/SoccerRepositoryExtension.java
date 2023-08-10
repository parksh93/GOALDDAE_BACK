package com.goalddae.repository;

import com.goalddae.entity.SoccerFiled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SoccerRepositoryExtension {
    List<SoccerFiled> findByKeyword(String keyword);
}
