package com.goalddae.repository;

import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.entity.ReservationField;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationFieldJPARepository extends JpaRepository<ReservationField, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ReservationField> findBySoccerFieldIdAndReservedDate(long fieldId, LocalDateTime reservedDate);
    // 해당 시간의 예약 확인
    List<ReservationField> findBySoccerFieldIdAndReservedDateBetween(long fieldId,
                                                                     LocalDateTime startDate,
                                                                     LocalDateTime endDate);
}