package com.goalddae.dto.friend;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FindFriendAcceptDTO {
    private long id;
    private String nickname;
    private String profileImgUrl;
    private Date acceptDate;
    private int accept;
}
