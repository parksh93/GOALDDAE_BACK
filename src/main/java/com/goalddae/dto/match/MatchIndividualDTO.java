package com.goalddae.dto.match;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MatchIndividualDTO {
    private long id;
    private int likes;
    private int views;
    private String gender;
    private int minimum;
    private int maximum;
    private int playerNumber;
    private LocalDateTime recruitStart;
    private LocalDateTime recruitEnd;
    private String managerId;

    private Long soccerField;
    private Long fieldReservation;
    private Long userId;
}