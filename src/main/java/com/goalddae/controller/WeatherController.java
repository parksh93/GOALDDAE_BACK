package com.goalddae.controller;

import com.goalddae.service.KakaoMapService;
import com.goalddae.service.WeatherService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private KakaoMapService kakaoMapService;
    private WeatherService weatherService;

    public WeatherController(KakaoMapService kakaoMapService, WeatherService weatherService){
        this.kakaoMapService = kakaoMapService;
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "/getNowWeather", method = RequestMethod.POST)
    public void getNowWeather(@RequestBody(required = false) long userId) throws Exception{
        Map<String, String> xyMap = kakaoMapService.getXY(userId);

        weatherService.getTodayWeather(xyMap);
    }
}
