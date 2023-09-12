package com.goalddae.dto.weather;

import com.goalddae.entity.Weather;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {
    private Long id;
    private String temperature; // 기온
    private String precipitation;   // 1시간 강수량
    private String sky; // 하늘 상태
    private String windDirection;   // 풍향
    private String windSpeed;   // 풍속
    private String city;
    private String fcstTime;

    public Weather toEntity(){
        Weather weather = Weather.builder()
                .id(id)
                .temperature(temperature)
                .precipitation(precipitation)
                .sky(sky)
                .windDirection(windDirection)
                .windSpeed(windSpeed)
                .city(city)
                .fcstTime(fcstTime)
                .build();

        return weather;
    }
}
