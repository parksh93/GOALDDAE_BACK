package com.goalddae.dto.match;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IndividualMatchDetailDTO {
    private long id;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private String gender;
    private long playerNumber;
    private String limitLevel;
    private String playDate;

    // 구장 정보
    private long fieldId;
    private String fieldImg1;
    private String fieldImg2;
    private String fieldImg3;
    private String province;
    private String region;
    private String address;
    private String fieldName;
    private String fieldSize;
    private String grassWhether;
    private String inOutWhether;
    private boolean parkingStatus;
    private boolean showerStatus;
    private boolean toiletStatus;
    private String content;

    // 매치 생성자 정보
    private long userId;
    private String nickname;
    private String profileImgUrl;
    private String level;
}
