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
    // 예약 가능 시간
    private List<LocalTime> availableTimes;
    // 예약 불가능 시간
    private List<LocalTime> reservedTimes;
}
