package com.goalddae.dto.fieldReservation;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FieldReservationInfoDTO {
    private List<LocalTime> availableTimes;
    private List<LocalTime> reservedTimes;
}
