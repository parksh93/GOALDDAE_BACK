package com.goalddae.dto.weather;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {
    private String temperature;
    private String precipitation;
    private String sky;
    private String windDirection;
    private String windSpeed;
}
