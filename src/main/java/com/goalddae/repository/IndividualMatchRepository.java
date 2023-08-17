package com.goalddae.repository;

import com.goalddae.dto.individualMatch.IndividualMatchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IndividualMatchRepository {
    List<IndividualMatchDTO> findAll();

    IndividualMatchDTO findById(@Param("id") Long id);

    void save(IndividualMatchDTO individualMatchDTO);

    void update(IndividualMatchDTO individualMatchDTO);

    void deleteById(@Param("id") Long id);

    void createIndividualMatchTable(@Param("individualMatch") String individualMatch);
}