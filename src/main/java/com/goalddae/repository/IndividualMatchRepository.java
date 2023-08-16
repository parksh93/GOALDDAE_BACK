package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IndividualMatchRepository {
    void individualMatch(@Param("individualMatch") String individualMatch);
}
