package com.goalddae.dto.match;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class IndividualMatchDTO {
    private long id;
    private long fieldId;
    private LocalDateTime startTime;
    private String fieldName;
    private String status;
    private long playerNumber;
    private String gender;
    private long userId;
}
