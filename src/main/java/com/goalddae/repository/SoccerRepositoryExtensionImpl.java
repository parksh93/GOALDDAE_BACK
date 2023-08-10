package com.goalddae.repository;

import com.goalddae.entity.SoccerFiled;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class SoccerRepositoryExtensionImpl extends QuerydslRepositorySupport implements SoccerRepositoryExtension {

    public SoccerRepositoryExtensionImpl() {
        super(SoccerFiled.class);
    }

    @Override
    public List<SoccerFiled> findByKeyword(String keyword) {
        SoccerFiled soccerFiled = SoccerFiled.
        return null;
    }
}
