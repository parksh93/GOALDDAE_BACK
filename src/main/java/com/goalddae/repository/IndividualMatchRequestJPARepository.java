package com.goalddae.repository;

import com.goalddae.dto.match.GetPlayerInfoDTO;
import com.goalddae.dto.match.IndividualMatchRequestDTO;
import com.goalddae.entity.IndividualMatchRequest;
import com.goalddae.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IndividualMatchRequestJPARepository extends JpaRepository<IndividualMatchRequest, Long> {
    List<IndividualMatchRequest> findAllByUserId(Long userId);

    @Transactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    int countByIndividualMatchId(long individualMatchId);

    @Query(value = "SELECT u.id, u.nickname, u.profile_img_url AS profileImgUrl, u.level FROM individual_match_request i JOIN users u ON i.user_id = u.id WHERE i.individual_match_id = :matchId", nativeQuery = true)
    List<GetPlayerInfoDTO> findIndividualMatchPlayerList(@Param("matchId") long matchId);

    @Modifying
    @Transactional
    void deleteByUserIdAndIndividualMatchId(long userId, long individualMatchId);
}
