package com.goalddae.dto.weather;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetWeatherDTO {
    private String city;
}
