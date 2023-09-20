package com.goalddae.dto.match;

import java.sql.Date;
import java.time.LocalDateTime;

public interface GetPlayerInfoDTO {
    long getId();
    String getNickname();
    String getGender();
    String getProfileImgUrl();
    String getLevel();
}
