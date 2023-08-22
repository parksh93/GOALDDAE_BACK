package com.goalddae.dto.friend;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendBlockDTO {
    private long id;
    private String blockUserId;
    private LocalDateTime blockDate;
    private long userId;
}
