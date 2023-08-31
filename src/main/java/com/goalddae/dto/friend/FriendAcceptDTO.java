package com.goalddae.dto.friend;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendAcceptDTO {
    private long id;
    private String fromUser;
    private LocalDateTime acceptDate;
    private int accept;
    private long userId;
}
