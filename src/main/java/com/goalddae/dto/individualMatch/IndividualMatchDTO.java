package com.goalddae.dto.individualMatch;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IndividualMatchDTO {
    private Long id;
    private int like;
    private int views;
    private String individualGender;
    private int individualMinimum;
    private int individualMaximum;
    private int individualNumber;
    private LocalDateTime recruitStart;
    private LocalDateTime recruitEnd;
    private String managerId;

    // 외래키
    private Long soccerField;
    private Long fieldReservation;
    private Long users;
}