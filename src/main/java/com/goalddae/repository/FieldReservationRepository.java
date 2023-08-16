package com.goalddae.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FieldReservationRepository {
    void fieldReservation(@Param("fieldReservation") String fieldReservation);
}
