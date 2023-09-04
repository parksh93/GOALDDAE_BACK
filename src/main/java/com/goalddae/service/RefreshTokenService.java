package com.goalddae.service;

import com.goalddae.entity.RefreshToken;

public interface RefreshTokenService {
    void saveRefreshToken(Long userId, String newRefreshToken);
    RefreshToken findByUserId(long userId);
}
