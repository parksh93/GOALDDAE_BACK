package com.goalddae.repository;

import com.goalddae.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
    void deleteByFcstTime(String fsctTime);
    List<Weather> findByCity(String city);
}
