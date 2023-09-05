package com.goalddae.service;

import com.goalddae.entity.RefreshToken;
import com.goalddae.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshServiceImpl implements RefreshTokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);

        if(refreshToken != null){
            refreshToken.update(newRefreshToken);
        }else{
            refreshToken = new RefreshToken(userId, newRefreshToken);
        }

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findByUserId(long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}
