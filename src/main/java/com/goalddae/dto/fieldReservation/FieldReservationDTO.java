package com.goalddae.dto.fieldReservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FieldReservationDTO {
    private String id;
    private LocalDateTime reservationData;
    private int reservationTeamId;

}
