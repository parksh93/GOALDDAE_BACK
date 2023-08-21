package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FieldReservationRepository {
    void createFieldReservationTable(String fieldReservation);
}
