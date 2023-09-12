package com.goalddae.dto.friend.friendBlock;

import lombok.*;

import java.time.LocalDateTime;

public interface FindFriendBlockDTO {
    long getId();
    String getNickname();
    String getProfileImgUrl();
    LocalDateTime getBlockDate();
}
