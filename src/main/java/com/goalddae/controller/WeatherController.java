package com.goalddae.controller;

import com.goalddae.dto.user.GetUserInfoDTO;
import com.goalddae.dto.weather.GetWeatherDTO;
import com.goalddae.entity.Weather;
import com.goalddae.service.KakaoMapService;
import com.goalddae.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private WeatherService weatherService;

    public WeatherController(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "/getNowWeather", method = RequestMethod.POST)
    public List<Weather> getNowWeather(@RequestBody GetWeatherDTO getWeatherDTO) {
        return weatherService.findMyCityWeather(getWeatherDTO);
    }
}
