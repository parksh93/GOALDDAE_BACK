package com.goalddae.repository;


import com.goalddae.dto.match.MatchIndividualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MatchIndividualRepository {
    void createMatchIndividualTable(String matchIndividual);
    List<MatchIndividualDTO> findIndividualMatches(@Param("matchIndividualTable") String matchIndividualTable,
                                                   @Param("recruitStart") LocalDateTime recruitStart,
                                                   @Param("recruitEnd") LocalDateTime recruitEnd);


}