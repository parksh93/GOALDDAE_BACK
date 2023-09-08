package com.goalddae.dto.match;

import com.goalddae.entity.ReservationField;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IndividualMatchRequestDTO {

    private long id;
    private long playerNumber;
    private String level;
    private String gender;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
//    private ReservationField reservationField;

}
