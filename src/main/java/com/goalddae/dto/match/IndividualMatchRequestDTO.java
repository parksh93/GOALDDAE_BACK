package com.goalddae.dto.match;

import com.goalddae.entity.ReservationField;
import com.goalddae.entity.SoccerField;
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
    private SoccerField soccerField;

    // 매치 생성자 정보 추가
    private long creatorUserId;
    private String creatorNickname;
    private String creatorProfileImgUrl;
    private String creatorLevel;
}
