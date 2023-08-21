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
    private long id;
    private String reservationId;
    private LocalDateTime reservationDate;
    private int reservationTeamWhether;
    private String reservationTeamId;
    private Long SoccerFieldId;
}
