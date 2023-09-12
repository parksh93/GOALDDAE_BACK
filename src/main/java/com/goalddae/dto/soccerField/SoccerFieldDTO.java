package com.goalddae.dto.soccerField;

import com.goalddae.dto.fieldReservation.FieldReservationInfoDTO;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SoccerFieldDTO {
    private long id;
    private String  fieldName;
    private LocalTime operatingHours;
    private LocalTime closingTime;
    private int playerCapacity;
    private String region;
    private int reservationFee;
    private String fieldSize;
    private String inOutWhether;
    private String grassWhether;
    private boolean toiletStatus;
    private boolean showerStatus;
    private boolean parkingStatus;
    private String fieldImg1;
    private String fieldImg2;
    private String fieldImg3;
    private String province;
    private FieldReservationInfoDTO reservationInfo;
}
