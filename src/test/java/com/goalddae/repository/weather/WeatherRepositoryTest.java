package com.goalddae.repository.weather;

import com.goalddae.entity.Weather;
import com.goalddae.repository.WeatherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WeatherRepositoryTest {
    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    @Transactional
    @DisplayName("이전 시간대 조회된 날씨 삭제")
    public void deleteByFcstTimeTest() {
        String fcstTime = "0600";

        weatherRepository.deleteByFcstTime(fcstTime);
    }

    @Test
    @Transactional
    @DisplayName("지역의 날씨 조회")
    public void findByCityTest() {
        String city = "서울";

        List<Weather> weather = weatherRepository.findByCity(city);

        assertEquals("강수없음", weather.get(0).getPrecipitation());
    }
}
