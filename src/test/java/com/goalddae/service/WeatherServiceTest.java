package com.goalddae.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("현재 날씨 가져오기")
    public void getTodayWeatherTest() throws Exception{
        Map<String, String> xyMap = new HashMap<>();
        xyMap.put("x", "127");
        xyMap.put("y", "37");

        weatherService.getTodayWeather(xyMap);
    }
}
