package com.goalddae.dto.match;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatchInfoDTO {
    private long id;
    private long fieldId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String fieldName;
    private String status;
    private long playerNumber;
    private String gender;
    private boolean toiletStatus;
    private boolean showerStatus;
    private boolean parkingStatus;
    private String fieldSize;
    private String grassWhether;
    private String inOutWhether;
    private String province;
    private String region;
    private String address;
    private String fieldImg1;
    private String fieldImg2;
    private String fieldImg3;
}