package com.goalddae.service;

import com.goalddae.entity.Weather;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("현재 날씨 API 요청 보내 가져와 저장")
    public void saveWeatherApiTest() throws Exception{
        weatherService.saveWeatherApi();
    }

    @Test
    @Transactional
    @DisplayName("현재 날씨 조회")
    public void findWeatherTest() {
        String city = "서울";
        List<Weather> weatherList = weatherService.findMyCityWeather(city);

        assertEquals("강수없음", weatherList.get(0).getPrecipitation());
    }
}
