package com.goalddae.service;

import org.apache.ibatis.annotations.Param;

public interface MatchService {
    boolean createMatchTeamTable(@Param("fieldId") Long fieldId);
    boolean createMatchIndividualTable(@Param("fieldId") Long fieldId);
    boolean dropMatchTeamTable(@Param("fieldId") Long fieldId);
    boolean dropMatchIndividualTable(@Param("fieldId") Long fieldId);
}

