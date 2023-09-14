package com.goalddae.dto.fieldReservation;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FieldReservationInfoDTO {
    private LocalDate date;
    private List<LocalTime> availableTimes;  // 예약 가능한 시간대
    private List<LocalTime> unavailableTimes;  // 예약 불가능한 시간대
    private List<LocalTime> ReservedTimes;
}