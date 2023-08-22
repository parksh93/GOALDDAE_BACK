package com.goalddae.dto.friend;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FriendAddDTO {
    private long id;
    private String toUser;
    private LocalDateTime requestDate;
    private long userId;
}
