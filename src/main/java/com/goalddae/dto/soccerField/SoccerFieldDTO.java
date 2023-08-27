package com.goalddae.dto.soccerField;

import com.goalddae.entity.SoccerField;
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
    private int toiletStatus;
    private int showerStatus;
    private int parkingStatus;
    private String fieldSize;
    private String fieldImg1;
    private String fieldImg2;
    private String fieldImg3;
    private int reservationFee;
    private String inOutWhether;
    private String grassWhether;
    private String region;


}
