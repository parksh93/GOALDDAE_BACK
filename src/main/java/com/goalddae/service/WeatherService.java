package com.goalddae.service;

import com.goalddae.dto.weather.GetWeatherDTO;
import com.goalddae.dto.weather.WeatherDTO;

import java.util.Map;

public interface WeatherService {
    WeatherDTO getWeatherApi(GetWeatherDTO getWeatherDTO, Map<String, String> xyMap) throws Exception;
}
