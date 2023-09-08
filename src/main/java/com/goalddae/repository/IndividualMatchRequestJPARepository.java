package com.goalddae.repository;

import com.goalddae.dto.match.IndividualMatchRequestDTO;
import com.goalddae.entity.IndividualMatchRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndividualMatchRequestJPARepository extends JpaRepository<IndividualMatchRequest, Long> {
    List<IndividualMatchRequest> findAllByUserId(Long userId);
}
