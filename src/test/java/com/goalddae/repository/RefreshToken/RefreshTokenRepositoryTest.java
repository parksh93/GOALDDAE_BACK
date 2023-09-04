package com.goalddae.repository.RefreshToken;

import com.goalddae.entity.RefreshToken;
import com.goalddae.repository.RefreshTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RefreshTokenRepositoryTest {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Test
    @Transactional
    public void findByUserId(){
        long userId = 1;

        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId);

        assertEquals(refreshToken.getUserId(), userId);
    }
}
