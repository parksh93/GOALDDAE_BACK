package com.goalddae.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class KaKaoMapServiceTest {
    @Autowired
    private KakaoMapService kaKaoMapService;

    @Test
    @DisplayName("유저가 선택한 주소의 x, y 좌표값 가져오기")
    public void getXY() {
        long userId = 1;

        kaKaoMapService.getXY(userId);
    }
}
