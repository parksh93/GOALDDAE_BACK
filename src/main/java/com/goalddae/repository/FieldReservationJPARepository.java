package com.goalddae.repository;

import com.goalddae.entity.ReservationField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldReservationJPARepository extends JpaRepository<ReservationField, Long> {
}

