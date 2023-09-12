package com.goalddae.service;

import com.goalddae.dto.weather.GetWeatherDTO;
import com.goalddae.entity.Weather;

import java.util.List;
import java.util.Map;

public interface WeatherService {
    void saveWeatherApi() throws Exception;
    List<Weather> findMyCityWeather(GetWeatherDTO getWeatherDTO);
}
