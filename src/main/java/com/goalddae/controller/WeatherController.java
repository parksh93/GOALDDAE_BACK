package com.goalddae.controller;

import com.goalddae.dto.weather.GetWeatherDTO;
import com.goalddae.dto.weather.WeatherDTO;
import com.goalddae.service.KakaoMapService;
import com.goalddae.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private WeatherService weatherService;
    private KakaoMapService kakaoMapService;

    public WeatherController(WeatherService weatherService, KakaoMapService kakaoMapService){
        this.weatherService = weatherService;
        this.kakaoMapService = kakaoMapService;
    }

    @RequestMapping(value = "/getNowWeather", method = RequestMethod.POST)
    public ResponseEntity<WeatherDTO> getNowWeather(@RequestBody GetWeatherDTO getWeatherDTO) throws Exception {
        Map<String, String> xyMap = kakaoMapService.getXY(getWeatherDTO.getCity());
        return ResponseEntity.ok(weatherService.getWeatherApi(getWeatherDTO, xyMap));
    }
}
