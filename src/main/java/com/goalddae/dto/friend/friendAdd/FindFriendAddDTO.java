package com.goalddae.dto.friend.friendAdd;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindFriendAddDTO {
    private String nickname;
    private String profileImgUrl;
    private int accept;
    private LocalDateTime acceptDate;
    private LocalDateTime requestDate;
    private long id;
}
