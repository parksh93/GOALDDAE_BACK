package com.goalddae.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamMatchUserInfoDTO {
    private long id;
    private Long teamId;
    private String nickname;
    private String profileImgUrl;
}
