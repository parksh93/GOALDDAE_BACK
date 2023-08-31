package com.goalddae.repository;


import com.goalddae.dto.match.MatchIndividualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MatchIndividualRepository {
    void createMatchIndividualTable(@Param("id") Long matchIndividualId);
    void dropMatchIndividualTable(@Param("id") Long matchIndividualId);
}