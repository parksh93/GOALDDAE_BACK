package com.goalddae.dto.match;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExtendedIndividualMatchRequestDTO extends IndividualMatchRequestDTO{

    // 매치 생성자 정보
    private long creatorUserId;

}
