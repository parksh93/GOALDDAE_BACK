package com.goalddae.repository;

import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.entity.ReservationField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationFieldJPARepository extends JpaRepository<ReservationField, Long> {
    List<ReservationField> findBySoccerFieldIdAndReservedDate(long fieldId, LocalDateTime reservedDate);
    // 해당 시간의 예약 확인
    List<ReservationField> findBySoccerFieldIdAndReservedDateBetween(long fieldId,
                                                                     LocalDateTime startDate,
                                                                     LocalDateTime endDate);
}


