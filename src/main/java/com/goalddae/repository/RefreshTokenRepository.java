package com.goalddae.repository;

import com.goalddae.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public RefreshToken findByUserId(long userId);
}
