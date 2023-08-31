package com.goalddae.service;

import org.apache.ibatis.annotations.Param;

public interface FieldReservationService {
    boolean dropFieldReservationTable(@Param("fieldId") Long fieldId);
    boolean createFieldReservationTable(@Param("fieldId") Long fieldId);
}