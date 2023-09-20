package com.goalddae.dto.match;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaveIndividualMatchDTO {
    private long userId;
    private long matchId;
}
