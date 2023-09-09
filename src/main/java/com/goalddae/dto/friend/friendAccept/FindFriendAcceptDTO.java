package com.goalddae.dto.friend.friendAccept;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

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
    private LocalDateTime requestDate;
}
