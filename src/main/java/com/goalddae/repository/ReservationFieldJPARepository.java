package com.goalddae.repository;

import com.goalddae.entity.ReservationField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationFieldJPARepository extends JpaRepository<ReservationField, Long> {
    List<ReservationField> findBySoccerFieldIdAndReservedDate(long fieldId, LocalDateTime reservedDate);
}


