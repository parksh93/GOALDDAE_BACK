package com.goalddae.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String temperature;

    @Column(nullable = false)
    private String precipitation;

    @Column(nullable = false)
    private String sky;

    @Column(nullable = false)
    private String windDirection;

    @Column(nullable = false)
    private String windSpeed;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String fcstTime;
}
