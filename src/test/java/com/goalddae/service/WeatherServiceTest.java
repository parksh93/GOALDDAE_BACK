package com.goalddae.service;

import com.goalddae.dto.weather.GetWeatherDTO;
import com.goalddae.dto.weather.WeatherDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WeatherServiceTest {
    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("현재 날씨 API 요청")
    public void saveWeatherApiTest() throws Exception {
        GetWeatherDTO weatherDTO = GetWeatherDTO.builder().city("경기").build();
        Map<String, String> xyMap = new HashMap<>();
        xyMap.put("x", "127");
        xyMap.put("y", "37");
        WeatherDTO weatherDTO1 = weatherService.getWeatherApi(weatherDTO, xyMap);

        assertEquals("23", weatherDTO1.getTemperature());
    }
}
