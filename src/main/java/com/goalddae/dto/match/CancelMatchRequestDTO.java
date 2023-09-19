package com.goalddae.dto.match;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CancelMatchRequestDTO {
    private long userId;
    private long matchId;
}
