package com.goalddae.dto.soccerField;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SoccerFieldDTO {
    private long id;
    private String  fieldName;
    private boolean toiletStatus;
    private boolean showerStatus;
    private boolean parkingStatus;
    private String fieldSize;
    private String fieldImg1;
    private String fieldImg2;
    private String fieldImg3;
    private int reservationFee;
    private String inOutWhether;
    private String grassWhether;
    private String region;
}