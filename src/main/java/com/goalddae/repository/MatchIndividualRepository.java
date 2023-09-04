package com.goalddae.repository;

import com.goalddae.dto.match.MatchIndividualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MatchIndividualRepository {
    void createMatchIndividualTable(@Param("id") Long matchIndividualId);
    void dropMatchIndividualTable(@Param("id") Long matchIndividualId);
    List<MatchIndividualDTO> findAllByUserId(String userId);
}
