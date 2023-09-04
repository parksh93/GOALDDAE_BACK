package com.goalddae.service;

import com.goalddae.dto.match.MatchIndividualDTO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MatchService {
    boolean createMatchTeamTable(@Param("fieldId") Long fieldId);
    boolean createMatchIndividualTable(@Param("fieldId") Long fieldId);
    boolean dropMatchTeamTable(@Param("fieldId") Long fieldId);
    boolean dropMatchIndividualTable(@Param("fieldId") Long fieldId);
    List<MatchIndividualDTO> findAllByUserId(String userId);

}

