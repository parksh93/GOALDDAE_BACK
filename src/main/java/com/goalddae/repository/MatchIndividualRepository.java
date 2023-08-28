package com.goalddae.repository;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.goalddae.dto.match.MatchIndividualDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchIndividualRepository {
    void createMatchIndividualTable(String matchIndividual);
    List<MatchIndividualDTO> findAllByUserId(String userId);
}
