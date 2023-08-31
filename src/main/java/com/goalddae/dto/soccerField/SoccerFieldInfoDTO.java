package com.goalddae.dto.soccerField;

import com.goalddae.entity.SoccerField;
import lombok.*;

import java.util.Scanner;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SoccerFieldInfoDTO {
    private long id;
    private String fieldName;
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

    public SoccerFieldInfoDTO(SoccerField soccerField){
        this.id = soccerField.getId();
        this.fieldName = soccerField.getFieldName();
        this.toiletStatus = soccerField.isToiletStatus();
        this.showerStatus = soccerField.isShowerStatus();
        this.parkingStatus = soccerField.isParkingStatus();
        this.fieldSize = soccerField.getFieldSize();
        this.fieldImg1 = soccerField.getFieldImg1();
        this.fieldImg2 = soccerField.getFieldImg2();
        this.fieldImg3 = soccerField.getFieldImg3();
        this.reservationFee = soccerField.getReservationFee();
        this.inOutWhether = soccerField.getInOutWhether();
        this.grassWhether = soccerField.getGrassWhether();
        this.region = soccerField.getRegion();
    }

    public SoccerField toEntity(){
        return SoccerField.builder()
                .id(id)
                .fieldName(fieldName)
                .toiletStatus(toiletStatus)
                .parkingStatus(parkingStatus)
                .showerStatus(showerStatus)
                .fieldImg1(fieldImg1)
                .fieldImg2(fieldImg2)
                .fieldImg3(fieldImg3)
                .fieldSize(fieldSize)
                .inOutWhether(inOutWhether)
                .grassWhether(grassWhether)
                .reservationFee(reservationFee)
                .region(region)
                .build();
    }

}
