package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FieldReservationRepository {
    void createFieldReservationTable(@Param("id") Long soccerFieldId);
    void dropFieldReservationTable(@Param("id") Long soccerFieldId);
}

